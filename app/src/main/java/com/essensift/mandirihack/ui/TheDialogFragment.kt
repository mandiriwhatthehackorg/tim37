package com.essensift.mandirihack.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.essensift.mandirihack.R

class TheDialogFragment : DialogFragment() {

    //Usage
    /*//Show Dialog
    val f = TheDialogFragment.newInstance(checkOutJourney!!)
    f.setTargetFragment(this@PaymentFragment, 3)
    f.show(fragmentManager!!, "fragment_pay_confirm")*/

    companion object {
        private const val TAG = "DIALOG_FRAG"

        fun newInstance(): TheDialogFragment {
            val fragment = TheDialogFragment()
            val args = Bundle()
            //args.putSerializable("DATA", journey)
            fragment.arguments = args
            return fragment
        }
    }

    interface FinishDialogListener {
        //fun onFinishDialog(journey: Journey)
        fun onFinishDialog()
    }

    //private var currentJourney: Journey? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_the, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getSerializable("DATA").let {
            //currentJourney = it as Journey


            /*btnPayConfirmSaldo.setOnClickListener {
                val listener = targetFragment as FinishDialogListener
                currentJourney?.paymentMethod = "SALDO"
                listener.onFinishDialog(currentJourney!!)
                dismiss()
            }*/
        }
    }


}