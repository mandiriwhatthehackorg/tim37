package com.essensift.mandirihack.ui.auth

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
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
import com.essensift.mandirihack.database.DBEngineHelper
import com.essensift.mandirihack.engine.GenericEngine
import com.essensift.mandirihack.engine.SharedPref
import com.essensift.mandirihack.engine.TransitionAnim
import com.essensift.mandirihack.ui.emoney.GuideActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import pub.devrel.easypermissions.EasyPermissions

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MAIN_ACTIVITY"
    }

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mFirestore: FirebaseFirestore = DBEngineHelper.dbEngine.getFirestore()
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        pref = SharedPref.getPref(this@LoginActivity, SharedPref.PREF_ID)

        mAuth.addAuthStateListener(mAuthStateListener)

        addFragment(IntroFragment(), frameLoginFragmentContainer.id)
        pbLoginLoading.visibility = View.GONE

        if (mAuth.currentUser == null) {
            //loadAllDriverData()
        }

        Log.d(TAG, "Current User ID ${mAuth.currentUser}")

        //askPermission()

        GenericEngine.uiThread.postDelayed({
            TransitionAnim.performTransitionAnim(
                this,
                frameLoginFragmentContainer.id,
                supportFragmentManager,
                LoginFragment(),
                Fade(),
                Slide()
            )
        }, 2000)
    }

    private fun askPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {

        } else {
            EasyPermissions.requestPermissions(this, "Aplikasi membutuhkan izin akses lokasi. Izinkan?",
                1,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    private val mAuthStateListener = FirebaseAuth.AuthStateListener { auth ->
        if (auth.currentUser != null) {
            /*startActivity(Intent(this, EmoneyActivity::class.java))
            finish()*/
        }
    }

    fun loginUserAnonymously() {
        Log.d(TAG, "Remove auth listener")
        /*mAuth.removeAuthStateListener(mAuthStateListener)
        mAuth.signInAnonymously().addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this, EmoneyActivity::class.java))
                finish()
            } else {
                ShowDialog.showErrorDialog(
                    this,
                    "Kesalahan koneksi saat masuk ${it.exception?.message}"
                )
            }
            mAuth.addAuthStateListener(mAuthStateListener)
        }*/
        startActivity(Intent(this, GuideActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        mAuth.removeAuthStateListener(mAuthStateListener)
        super.onDestroy()
    }

    override fun onStop() {
        mAuth.removeAuthStateListener(mAuthStateListener)
        super.onStop()
    }

    override fun onPause() {
        mAuth.removeAuthStateListener(mAuthStateListener)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mAuth.addAuthStateListener(mAuthStateListener)
    }

    fun replaceAdminLogin() {
        replaceFragment(LoginFragment(), frameLoginFragmentContainer.id)
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

}
