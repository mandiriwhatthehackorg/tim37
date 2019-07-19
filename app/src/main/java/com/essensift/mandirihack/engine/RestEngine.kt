package com.essensift.mandirihack.engine

import android.content.Context
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.essensift.mandirihack.database.model.DistanceMatrixApiResponse
import com.essensift.mandirihack.database.model.GeoLoc
import com.essensift.mandirihack.database.model.ReverseGeocodingResponse
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.URL
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

object RestEngine {

    private const val TAG = "REST_ENGINE"
    private var volleyRequestQueue: RequestQueue? = null

    private fun sslInit(context: Context): SSLSocketFactory {
        // Load CAs from an InputStream
        // (could be from a resource or ByteArrayInputStream or ...)
        val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
        // From https://www.washington.edu/itconnect/security/ca/load-der.crt
        val caInput: InputStream = BufferedInputStream(context.assets.open("primary.crt"))
        val ca: X509Certificate = caInput.use {
            cf.generateCertificate(it) as X509Certificate
        }
        println("ca=" + ca.subjectDN)

        // Create a KeyStore containing our trusted CAs
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType).apply {
            load(null, null)
            setCertificateEntry("ca", ca)
        }

        // Create a TrustManager that trusts the CAs inputStream our KeyStore
        val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
        val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm).apply {
            init(keyStore)
        }

        // Create an SSLContext that uses our TrustManager
        val c: SSLContext = SSLContext.getInstance("TLS").apply {
            init(null, tmf.trustManagers, null)
        }

        // Tell the URLConnection to use a SocketFactory from our SSLContext
        return c.socketFactory
    }

    private fun initConnection(context: Context): HurlStack {
        return object : HurlStack() {
            @Throws(IOException::class)
            override fun createConnection(url: URL): HttpURLConnection {
                val httpsURLConnection = super.createConnection(url) as HttpsURLConnection
                try {
                    Log.d(TAG, "Step 2")
                    val hostnameVerifier = HostnameVerifier { _, session ->
                        HttpsURLConnection.getDefaultHostnameVerifier().run {
                            verify("apigateway.mandiriwhatthehack.com", session)
                            Log.d(TAG, "Step 3")
                            return@run true
                        }
                    }
                    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }

                        override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {}

                        override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {}
                    })

                    val sc = SSLContext.getInstance("SSL")
                    sc.init(null, trustAllCerts, SecureRandom())
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
                    httpsURLConnection.sslSocketFactory = sc.socketFactory
                    httpsURLConnection.hostnameVerifier = hostnameVerifier

                    Log.d(TAG, "Success here")
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return httpsURLConnection
            }
        }
    }

    fun restRequest(context: Context, endPoint: String, method: Int, dataJson: String,
                    isSSL: Boolean, ignoreSSLValidation: Boolean, authToken: String = "", c: RestApiRawCallback) {

        volleyRequestQueue = if (isSSL) {
            if (ignoreSSLValidation) {
                val hurlStack: HurlStack = initConnection(context)
                Volley.newRequestQueue(context, hurlStack)
            } else
                Volley.newRequestQueue(context)
        } else
            Volley.newRequestQueue(context)

        //Log.d(TAG, "endpoint: $endPoint")
        val body: String = dataJson

        // Request a string response from the provided URL.
        val stringRequest = object : StringRequest(method, endPoint, Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                Log.d(TAG, "Response is: $response")
                c.onResponse(response)
            },
                Response.ErrorListener {
                    Log.d(TAG, "Error: ${it.message}")
                    c.onError(it)
                }) {

                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray? {
                    return try {
                        body.toByteArray(charset("utf-8"))
                    } catch (uee: UnsupportedEncodingException) {
                        VolleyLog.wtf(
                            "Unsupported Encoding while trying to get the bytes of %s using %s",
                            body,
                            "utf-8"
                        )
                        null
                    }
                }

                override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                    val responseString = response.statusCode.toString()
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response))
                }

                /** Passing some request headers*  */
                @Throws(AuthFailureError::class)
                override fun getHeaders(): HashMap<String, String> {
                    val headers = HashMap<String, String>()
                    if (authToken != "") {
                        headers["Content-Type"] = "application/json"
                        headers["apiKey"] = authToken
                    }
                    return headers
                }
            }

        // Add the request to the RequestQueue.
        volleyRequestQueue?.add(stringRequest)
    }

    fun getLocationDistance(context: Context, start: GeoLoc, end: GeoLoc, c: RestApiCallback) {
        if (volleyRequestQueue == null)
            volleyRequestQueue = Volley.newRequestQueue(context)

        // Instantiate the RequestQueue.
        val endPoint =
            "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=${start.latitude},${start.longitude}&destinations=${end.latitude},${end.longitude}&key=AIzaSyB21Jlg2bHC-00aMV_fa5eFOO3Ph9rot1g"
        //Log.d(TAG, "endpoint: $endPoint")

        // Request a string response from the provided URL.
        val stringRequest =
            StringRequest(Request.Method.GET, endPoint, Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                Log.d(TAG, "Response is: $response")
                try {
                    val d: DistanceMatrixApiResponse =
                        GsonHelper.gson.fromJson(response, DistanceMatrixApiResponse::class.java)
                    c.onResponse(d)
                } catch (e: Exception) {
                    Log.e(TAG, "'Error: " + e.message)
                }
            }, Response.ErrorListener {
                Log.d(TAG, "Error: ${it.message}")
                c.onError(it)
            })

        // Add the request to the RequestQueue.
        volleyRequestQueue?.add(stringRequest)
    }

    fun getReadableAdressFromLatLng(context: Context, geo: GeoLoc, c: RestApiCallback) {
        if (volleyRequestQueue == null)
            volleyRequestQueue = Volley.newRequestQueue(context)

        val endPoint =
            "https://maps.googleapis.com/maps/api/geocode/json?latlng=${geo.latitude},${geo.longitude}&key=AIzaSyB21Jlg2bHC-00aMV_fa5eFOO3Ph9rot1g"

        // Request a string response from the provided URL.
        val stringRequest =
            StringRequest(Request.Method.GET, endPoint, Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                Log.d(TAG, "Response is: $response")
                try {
                    val d: ReverseGeocodingResponse =
                        GsonHelper.gson.fromJson(response, ReverseGeocodingResponse::class.java)
                    c.onResponse(d)
                } catch (e: Exception) {
                    Log.e(TAG, "'Error: " + e.message)
                }
            }, Response.ErrorListener {
                Log.d(TAG, "Error: ${it.message}")
                c.onError(it)
            })

        // Add the request to the RequestQueue.
        volleyRequestQueue?.add(stringRequest)
    }

    interface RestApiCallback {
        fun onResponse(data: Any)

        fun onError(e: VolleyError)
    }

    interface RestApiRawCallback {
        fun onResponse(data: String)

        fun onError(e: VolleyError)
    }
}