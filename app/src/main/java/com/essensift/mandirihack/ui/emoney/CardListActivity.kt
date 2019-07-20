package com.essensift.mandirihack.ui.emoney

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.essensift.mandirihack.R

class CardListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_list)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
    }
}
