package com.essensift.mandirihack.engine

import android.app.Activity
import com.essensift.mandirihack.database.model.GeoLoc

object LocationHelper {

    fun getLatestLocation(activity: Activity, c: LocationEngine.OnLocationCallback) {
        LocationEngine().createLocationRequest(activity, c)
    }

    private fun getDistanceMetrix(start: GeoLoc, end: GeoLoc) {


    }
}