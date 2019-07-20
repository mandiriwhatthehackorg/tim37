package com.essensift.mandirihack.engine

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.essensift.mandirihack.ui.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.*

object GenericEngine {

    val uiThread = Handler(Looper.getMainLooper())

    fun signOut(activity: Activity) {
        FirebaseAuth.getInstance().signOut()
        activity.startActivity(Intent(activity, LoginActivity::class.java))
        activity.finish()
    }

    fun showDatePickerDialog(activity: Activity){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            activity,
            DatePickerDialog.OnDateSetListener { _, y, m, da ->
                //Code here
            },
            year,
            month,
            day
        )

        dpd.show()
    }
}