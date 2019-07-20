package com.essensift.mandirihack.ui.emoney

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.essensift.mandirihack.R
import com.essensift.mandirihack.database.model.Transaction
import com.essensift.mandirihack.engine.views.DividerItemDecoration
import com.essensift.mandirihack.engine.views.RecyclerTouchListener
import com.essensift.mandirihack.ui.emoney.adapter.AdapterRvTransaction
import io.opencensus.trace.MessageEvent
import kotlinx.android.synthetic.main.fragment_transaction.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class TransactionFragment : Fragment() {

    private lateinit var mAdapter: AdapterRvTransaction
    private val transactions: ArrayList<Transaction> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadDummy()

        mAdapter = AdapterRvTransaction(transactions, context!!)
        initRv(rvTransactions, mAdapter)
    }

    private fun loadDummy(){
        transactions.add(Transaction("1234567890", System.currentTimeMillis(), 1000L,
            "Mandiri", "Origin Channel", "originName",
            "1234", "Emoney ", "Soto Padang", 0L, ""
        )
        )
        transactions.add(Transaction("1234567890", System.currentTimeMillis(), 1000L,
            "Mandiri", "Origin Channel", "originName",
            "1234", "Emoney ", "Soto Padang", 0L, ""
        )
        )
        transactions.add(Transaction("1234567890", System.currentTimeMillis(), 1000L,
            "Mandiri", "Origin Channel", "originName",
            "1234", "Emoney ", "Soto Padang", 0L, ""
        )
        )
    }

    private fun initRv(recyclerView: RecyclerView, adapter: AdapterRvTransaction) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL))
        recyclerView.addOnItemTouchListener(RecyclerTouchListener(recyclerView.context, recyclerView, object : RecyclerTouchListener.ClickListener {
            override fun onClick(view: View?, position: Int) {

            }

            override fun onLongClick(view: View?, position: Int) {

            }
        }))
        //Adapter should be attached before selectionTracker declaration
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        /* Do something */
    }

}
