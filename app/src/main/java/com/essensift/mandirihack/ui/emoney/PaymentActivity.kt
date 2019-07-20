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
import androidx.transition.Fade
import androidx.transition.Slide
import com.essensift.mandirihack.R
import com.essensift.mandirihack.engine.GenericEngine
import com.essensift.mandirihack.engine.TransitionAnim
import github.nisrulz.qreader.QREader
import io.opencensus.trace.MessageEvent
import kotlinx.android.synthetic.main.activity_pay.*
import kotlinx.android.synthetic.main.activity_payment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.EasyPermissions

class PaymentActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "PAY_ACTIVITY"
    }

    private lateinit var qRReader: QREader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        checkPermission()

        intent?.getStringExtra("ID")?.let {
            if (it == "NFC") {
                TransitionAnim.performTransitionAnim(
                    this,
                    framePaymentFragmentContainer.id,
                    supportFragmentManager,
                    ReceivePaymentFragment(),
                    Fade(),
                    Slide()
                )
            } else {
                TransitionAnim.performTransitionAnim(
                    this,
                    framePaymentFragmentContainer.id,
                    supportFragmentManager,
                    PayQRFragment(),
                    Fade(),
                    Slide()
                )
                GenericEngine.uiThread.postDelayed({
                    TransitionAnim.performTransitionAnim(
                        this,
                        framePaymentFragmentContainer.id,
                        supportFragmentManager,
                        PayQRSuccessFragment(),
                        Fade(),
                        Slide()
                    )
                }, 20000)
            }
        }

    }

    fun transitionToNFC() {
        TransitionAnim.performTransitionAnim(
            this,
            framePaymentFragmentContainer.id,
            supportFragmentManager,
            PayNFCFragment(),
            Fade(),
            Slide()
        )
        GenericEngine.uiThread.postDelayed({
            TransitionAnim.performTransitionAnim(
                this,
                framePaymentFragmentContainer.id,
                supportFragmentManager,
                PayNFCSuccessFragment(),
                Fade(),
                Slide()
            )
        }, 4000)
    }

    fun backtoHome() {
        startActivity(Intent(this, EmoneyActivity::class.java))
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
        finish()
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
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.general_PermissionNeeded),
                2,
                *perms
            )
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
