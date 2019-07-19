package com.essensift.mandirihack.ui.emoney

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.essensift.mandirihack.R
import com.essensift.mandirihack.engine.GenericEngine
import github.nisrulz.qreader.QRDataListener
import github.nisrulz.qreader.QREader
import org.greenrobot.eventbus.EventBus
import io.opencensus.trace.MessageEvent
import kotlinx.android.synthetic.main.activity_pay.*
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe
import pub.devrel.easypermissions.EasyPermissions

class PayActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "PAY_ACTIVITY"
    }

    private lateinit var qRReader: QREader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        checkPermission()

    }

    private fun checkPermission() {
        val perms = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (EasyPermissions.hasPermissions(this, *perms)) {
            Log.d(TAG, "Camera run!")
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.general_PermissionNeeded), 2, *perms)
        }
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        /* Do something */
    }

}
