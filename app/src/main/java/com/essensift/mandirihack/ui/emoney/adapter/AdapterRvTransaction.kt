package com.essensift.mandirihack.ui.emoney.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.essensift.mandirihack.R
import com.essensift.mandirihack.database.model.Emoney
import com.essensift.mandirihack.database.model.Transaction
import kotlinx.android.synthetic.main.item_rv_manage.view.*

class AdapterRvTransaction(
    private val items: ArrayList<Transaction>,
    val context: Context
) : RecyclerView.Adapter<AdapterRvTransaction.AdapterViewHolder>() {

    companion object {
        private const val TAG = "ADAPTER_RV_NON"
    }

    // Gets the number of months in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        Log.d(TAG, "Created vh")
        return AdapterViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_rv_transaction,
                parent,
                false
            )
        )
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val t = items[position]

        holder.textName.text = t.id
        holder.textNumber.text = t.amount.toString()
    }

    class AdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val textName = view.textItemCardName!!
        val textNumber = view.textItemCardNumber!!
    }

}


