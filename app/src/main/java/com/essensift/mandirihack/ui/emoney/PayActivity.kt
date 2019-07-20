package com.essensift.mandirihack.ui.emoney

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.essensift.mandirihack.R
import github.nisrulz.qreader.QREader
import io.opencensus.trace.MessageEvent
import kotlinx.android.synthetic.main.activity_pay.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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

        btnPayPay.setOnClickListener {
            val i = Intent(this, PaymentActivity::class.java)
            i.putExtra("ID", "QR")
            startActivity(i)
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
        }

        btnPayReceive.setOnClickListener {
            val i = Intent(this, PaymentActivity::class.java)
            i.putExtra("ID", "NFC")
            startActivity(i)
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
    }

    fun closeFragment() {
        framePayFragmentContainer.visibility = View.GONE
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    private fun AppCompatActivity.removeFragment(
        fragment: Fragment
    ) {
        supportFragmentManager.inTransaction { remove(fragment) }
    }

    private fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { replace(frameId, fragment) }
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
