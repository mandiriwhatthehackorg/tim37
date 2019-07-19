package com.essensift.mandirihack.engine

import android.Manifest.permission
import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.location.Location
import android.util.Log
import com.essensift.mandirihack.database.model.GeoLoc
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import pub.devrel.easypermissions.EasyPermissions


class LocationEngine : EasyPermissions.PermissionCallbacks {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var lastActivity: Activity? = null
    private var lastCallback: OnLocationCallback? = null
    private var locationRequest: LocationRequest? = null

    companion object {
        private const val TAG = "LOCATION_ENGINE"
    }

    @SuppressLint("MissingPermission")
    fun getLatitudeLongitude(activity: Activity, c: OnLocationCallback) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

        if (EasyPermissions.hasPermissions(
                activity,
                permission.ACCESS_FINE_LOCATION,
                permission.ACCESS_COARSE_LOCATION
            )
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                Log.d(TAG, "Got location la:${location?.latitude} lo:${location?.longitude}")
                location?.let {
                    locationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult?) {
                            locationResult ?: return
                            for (loc in locationResult.locations) {
                                c.onSuccess(GeoLoc(loc.latitude, loc.longitude))
                                fusedLocationClient.removeLocationUpdates(locationCallback)
                            }
                        }
                    }
                    fusedLocationClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback, null
                    )

                } ?: run {
                    c.onError(Exception("Cannot get current location"))
                }
            }
        } else {
            // Do not have permissions, request them now
            lastActivity = activity
            lastCallback = c
            EasyPermissions.requestPermissions(
                activity, "NuNot membutuhkan izin akses lokasi. Izinkan?",
                1, permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION
            )
        }
    }

    fun createLocationRequest(activity: Activity, c: OnLocationCallback) {
        locationRequest = LocationRequest.create()?.apply {
            interval = 100000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
        val client: SettingsClient = LocationServices.getSettingsClient(activity)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            getLatitudeLongitude(activity, c)
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(activity, 2)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    interface OnLocationCallback {
        fun onSuccess(g: GeoLoc)

        fun onError(e: Exception)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        when (requestCode) {
            1 -> {
                if (lastActivity != null && lastCallback != null)
                    getLatitudeLongitude(lastActivity!!, lastCallback!!)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

    }
}