package com.essensift.mandirihack.ui.emoney

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.essensift.mandirihack.R
import com.essensift.mandirihack.engine.views.ViewPagerAdapterSaveState
import com.luseen.spacenavigation.SpaceItem
import com.luseen.spacenavigation.SpaceOnClickListener
import io.opencensus.trace.MessageEvent
import kotlinx.android.synthetic.main.activity_bottom_navigation.viewPagerDriveFragment
import kotlinx.android.synthetic.main.activity_emoney.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.EasyPermissions


class EmoneyActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    companion object {
        private const val TAG = "DRIVE_ACTIVITY"
    }

    private val adapter: ViewPagerAdapterSaveState = ViewPagerAdapterSaveState(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emoney)

        adapter.addFragment(ManageFragment(), "Atur E-Money")
        adapter.addFragment(TransactionFragment(), "Transaksi")

        adapter.addFragment(InvestFragment(), "Investasi")
        adapter.addFragment(EducateFragment(), "Edukasi")

        viewPagerDriveFragment.adapter = adapter
        viewPagerDriveFragment.offscreenPageLimit = 4

        space.addSpaceItem(SpaceItem("MANAGE", R.drawable.ic_icn_home))
        space.addSpaceItem(SpaceItem("TRANSACTION", R.drawable.ic_icn_profile))
        space.addSpaceItem(SpaceItem("INVESTMENT", R.drawable.ic_icn_invest))
        space.addSpaceItem(SpaceItem("POINTS", R.drawable.ic_icn_point))
        space.showIconOnly()
        space.setSpaceOnClickListener(object : SpaceOnClickListener {
            override fun onCentreButtonClick() {
                startActivity(Intent(this@EmoneyActivity, PayActivity::class.java))
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
            }

            override fun onItemReselected(itemIndex: Int, itemName: String?) {

            }

            override fun onItemClick(itemIndex: Int, itemName: String?) {
                when (itemIndex) {
                    0 -> {
                        viewPagerDriveFragment.setCurrentItem(0, false)
                    }
                    1 -> {
                        viewPagerDriveFragment.setCurrentItem(1, false)
                    }
                    2 -> {
                        viewPagerDriveFragment.setCurrentItem(2, false)
                    }
                    3 -> {
                        viewPagerDriveFragment.setCurrentItem(3, false)
                    }
                }
            }

        })

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
