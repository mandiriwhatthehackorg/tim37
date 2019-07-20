package com.essensift.mandirihack.ui.emoney

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.essensift.mandirihack.R
import com.essensift.mandirihack.engine.views.ViewPagerAdapterSaveState
import io.opencensus.trace.MessageEvent
import kotlinx.android.synthetic.main.activity_guide.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.EasyPermissions


class GuideActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    companion object {
        private const val TAG = "DRIVE_ACTIVITY"
    }

    private val adapter: ViewPagerAdapterSaveState =
        ViewPagerAdapterSaveState(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        adapter.addFragment(GuideFragment.newInstance("0"), "")
        adapter.addFragment(GuideFragment.newInstance("1"), "")
        adapter.addFragment(GuideFragment.newInstance("2"), "")

        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3

        /*navBottomEmoney.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuManage -> {
                    viewPagerDriveFragment.setCurrentItem(0, false)
                }
                R.id.menuTransaction -> {
                    viewPagerDriveFragment.setCurrentItem(1, false)
                }
                R.id.menuPay -> {
                    //viewPagerDriveFragment.setCurrentItem(2, false)
                }
                R.id.menuInvest -> {
                    viewPagerDriveFragment.setCurrentItem(2, false)
                }
                R.id.menuEducate -> {
                    viewPagerDriveFragment.setCurrentItem(3, false)
                }
            }
            true
        }*/
    }

    fun prosesButton(i: Int, next: Boolean) {
        if (next) {
            if (i == 2)
                startActivity(Intent(this, EmoneyActivity::class.java))
            else
                viewPager.setCurrentItem(i + 1, false)
        } else {
            if (i != 0) {
                viewPager.setCurrentItem(i - 1, false)
            }
        }

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
