package com.essensift.mandirihack.ui.emoney


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.essensift.mandirihack.R
import kotlinx.android.synthetic.main.fragment_guide.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GuideFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (param1 == "0") {
            imgGuide.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.screen1))
        } else if (param1 == "1") {
            imgGuide.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.screen2))
        } else if (param1 == "2") {
            imgGuide.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.screen3))
        }

        btnnext.setOnClickListener {
            (activity as GuideActivity).prosesButton(param1?.toInt()!!, true)
        }

        btnback.setOnClickListener {
            (activity as GuideActivity).prosesButton(param1?.toInt()!!, false)
        }
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            GuideFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}
