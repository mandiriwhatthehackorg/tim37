package com.essensift.mandirihack.engine

import android.content.Context
import android.content.SharedPreferences

object SharedPref {

    const val PREF_ID = "NU_NOT_KANTOR"
    const val LATEST_LOGIN_ID = "LATEST_LOGIN_ID"
    const val LATEST_LOGIN_ADMIN = "LATEST_LOGIN_ADMIN"

    private lateinit var editor: SharedPreferences.Editor

    fun getPref(context: Context, prefId: String): SharedPreferences {
        return context.getSharedPreferences(prefId, Context.MODE_PRIVATE)
    }

    fun editPref(pref: SharedPreferences, key: String, value: String) {
        editor = pref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun editPref(pref: SharedPreferences, key: String, value: Boolean) {
        editor = pref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun commitPref(pref: SharedPreferences) {
        editor = pref.edit()
        editor.apply()
    }
}