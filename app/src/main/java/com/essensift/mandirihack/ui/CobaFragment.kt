package com.essensift.mandirihack.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.VolleyError

import com.essensift.mandirihack.R
import com.essensift.mandirihack.engine.RestEngine
import com.essensift.mandirihack.engine.ShowDialog
import kotlinx.android.synthetic.main.fragment_coba.*

class CobaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coba, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnCoba1.setOnClickListener {
            RestEngine.restRequest(context!!, "https://apigateway.mandiriwhatthehack.com/gateway/CustomerExpAPI/1.0/complain",
                Request.Method.GET, "", isSSL = true, ignoreSSLValidation = true, authToken = "", c = object: RestEngine.RestApiRawCallback{
                    override fun onResponse(data: String) {
                        ShowDialog.showDoneDialog(context!!, "Response: $data")
                    }

                    override fun onError(e: VolleyError) {
                        ShowDialog.showErrorDialog(context!!, "Error: ${e.message}")
                    }

                })
        }
    }

}
