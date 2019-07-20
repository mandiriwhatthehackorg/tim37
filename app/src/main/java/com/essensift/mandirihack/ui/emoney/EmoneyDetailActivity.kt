package com.essensift.mandirihack.ui.emoney

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.essensift.mandirihack.R
import kotlinx.android.synthetic.main.activity_emoney_detail.*

class EmoneyDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emoney_detail)

        initSpinnerDriverList(spinnerCardDetailPilihanInvest)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
    }

    private val spinnerArray = ArrayList<String>()
    private fun initSpinnerDriverList(view: Spinner) {
        spinnerArray.clear()
        spinnerArray.add("Emas")
        spinnerArray.add("Reksadana")
        val adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, spinnerArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.adapter = adapter
    }
}
