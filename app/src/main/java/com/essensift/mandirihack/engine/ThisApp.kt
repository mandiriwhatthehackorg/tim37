package com.essensift.mandirihack.engine

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class ThisApp : MultiDexApplication() {

    companion object {
        lateinit var appContext: Context
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}