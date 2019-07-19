package com.essensift.mandirihack.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.essensift.mandirihack.R
import com.essensift.mandirihack.engine.views.ViewPagerAdapterSaveState
import io.opencensus.trace.MessageEvent
import kotlinx.android.synthetic.main.activity_bottom_navigation.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.EasyPermissions

class BottomNavigationActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    companion object {
        private const val TAG = "DRIVE_ACTIVITY"
    }

    private val adapter: ViewPagerAdapterSaveState = ViewPagerAdapterSaveState(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)

        adapter.addFragment(TheFragment(), "Pembayaran")
        adapter.addFragment(CobaFragment(), "Top Up")
        adapter.addFragment(TheFragment(), "Laporan")
        viewPagerDriveFragment.adapter = adapter
        viewPagerDriveFragment.offscreenPageLimit = 3

        /*navBottomDrive.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuPayment -> {
                    viewPagerDriveFragment.setCurrentItem(0, false)
                }
                R.id.menuTopUp -> {
                    viewPagerDriveFragment.setCurrentItem(1, false)

                }
                R.id.menuReport -> {
                    viewPagerDriveFragment.setCurrentItem(2, false)

                }
            }
            true
        }*/
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        /* Do something */
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        when (requestCode) {
            1 -> {

            }
        }
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    private fun AppCompatActivity.addFragment(
        fragment: Fragment,
        frameId: Int,
        tag: String = "DEFAULT"
    ) {
        supportFragmentManager.inTransaction { add(frameId, fragment, tag) }
    }

    private fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { replace(frameId, fragment) }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
