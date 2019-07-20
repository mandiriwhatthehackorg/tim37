package com.essensift.mandirihack.ui.emoney

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.essensift.mandirihack.R
import com.essensift.mandirihack.engine.GenericEngine
import github.nisrulz.qreader.QRDataListener
import github.nisrulz.qreader.QREader
import io.opencensus.trace.MessageEvent
import kotlinx.android.synthetic.main.fragment_scan_qr.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PayQRFragment : Fragment() {

    companion object {
        private const val TAG = "PAY_QR_SCAN_ACTIVITY"
    }

    private lateinit var qRReader: QREader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_qr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        qRReader = QREader.Builder(context!!, cameraViewScanQR, QRDataListener { data ->
            Log.e(TAG, "Value : $data")
            //Do add data to local database to be synced later.
            GenericEngine.uiThread.post {
                //checkAndAssignGuestByQRData(data)
            }
        }).facing(QREader.BACK_CAM)
            .enableAutofocus(true)
            .height(cameraViewScanQR.height)
            .width(cameraViewScanQR.width)
            .build()

        btnScanClose.setOnClickListener {
            (activity as PaymentActivity).backtoHome()
        }
    }

    override fun onStop() {
        Log.d(TAG, "on Stop Called!")
        qRReader.stop()
        qRReader.releaseAndCleanup()
        Log.d(TAG, "Camera Released")
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        if (!qRReader.isCameraRunning)
            qRReader.initAndStart(cameraViewScanQR)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "On Resume Called!")
        EventBus.getDefault().register(this)
        //
        try {
            if (!qRReader.isCameraRunning) {
                qRReader.initAndStart(cameraViewScanQR)
                Log.d(TAG, "Camera Started")
            } else {
                qRReader.start()
                Log.d(TAG, "Camera restarted")
            }
        } catch (ignored: Exception) {
        }
    }

    override fun onPause() {
        Log.d(TAG, "on Paused Called!")
        qRReader.stop()
        qRReader.releaseAndCleanup()
        Log.d(TAG, "Camera Released")
        EventBus.getDefault().unregister(this)
        super.onPause()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        /* Do something */
    }

}
