package com.essensift.mandirihack.ui.emoney


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.essensift.mandirihack.R
import kotlinx.android.synthetic.main.fragment_receive_payment.*

class ReceivePaymentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_receive_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnReceivePay.setOnClickListener {
            (activity as PaymentActivity).transitionToNFC()
        }

        btnReceiveClose.setOnClickListener {
            (activity as PaymentActivity).backtoHome()
        }
    }


}
