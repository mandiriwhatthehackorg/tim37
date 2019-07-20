package com.essensift.mandirihack.ui.emoney

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.essensift.mandirihack.R
import github.nisrulz.qreader.QREader
import io.opencensus.trace.MessageEvent
import kotlinx.android.synthetic.main.fragment_read_nfc.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PayNFCFragment : Fragment() {

    companion object {
        private const val TAG = "PAY_QR_SCAN_ACTIVITY"
    }

    private lateinit var qRReader: QREader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read_nfc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnreadnfClose.setOnClickListener {
            startActivity(Intent(activity!!, EmoneyActivity::class.java))
            activity?.overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
            activity!!.finish()
        }
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "On Resume Called!")
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        EventBus.getDefault().unregister(this)
        super.onPause()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        /* Do something */
    }

}
