package com.essensift.mandirihack.ui.emoney

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.essensift.mandirihack.R
import com.essensift.mandirihack.database.model.Emoney
import com.essensift.mandirihack.database.model.Transaction
import com.essensift.mandirihack.engine.views.DividerItemDecoration
import com.essensift.mandirihack.engine.views.RecyclerTouchListener
import com.essensift.mandirihack.ui.emoney.adapter.AdapterRvManage
import com.essensift.mandirihack.ui.emoney.adapter.AdapterRvTransaction
import io.opencensus.trace.MessageEvent
import kotlinx.android.synthetic.main.fragment_manage.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ManageFragment : Fragment() {

    private lateinit var mAdapter: AdapterRvManage
    private val cards: ArrayList<Emoney> = ArrayList()
    private lateinit var mAdapterTrans: AdapterRvTransaction
    private val transactions: ArrayList<Transaction> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*loadDummy()

        loadDummyTransaction()

        mAdapter = AdapterRvManage(cards, context!!)
        initRv(rvManageCard, mAdapter)

        mAdapterTrans = AdapterRvTransaction(transactions, context!!)
        initRv(rvTransactions, mAdapterTrans)*/

        btnEmoneyDetail.setOnClickListener {
            startActivity(Intent(activity!!, EmoneyDetailActivity::class.java))
            activity?.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
        }

        btnEmoneyList.setOnClickListener {
            startActivity(Intent(activity!!, CardListActivity::class.java))
            activity?.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
        }
    }

    private fun loadDummy(){
        cards.add(Emoney("1234567890", "Emoney Saya", 0))
        cards.add(Emoney("9876543211", "Emoney Istri", 0))
        cards.add(Emoney("3232323232", "Emoney Anak", 0))
    }

    private fun initRv(recyclerView: RecyclerView, adapter: AdapterRvManage) {
        recyclerView.layoutManager =
            LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                LinearLayoutManager.HORIZONTAL
            )
        )
        recyclerView.addOnItemTouchListener(
            RecyclerTouchListener(
                recyclerView.context,
                recyclerView,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View?, position: Int) {

                    }

                    override fun onLongClick(view: View?, position: Int) {

                    }
                })
        )
        //Adapter should be attached before selectionTracker declaration
        recyclerView.adapter = adapter
    }

    private fun loadDummyTransaction() {
        transactions.add(
            Transaction(
                "1234567890", System.currentTimeMillis(), 1000L,
                "Mandiri", "Origin Channel", "originName",
                "1234", "Emoney ", "Soto Padang", 0L, ""
            )
        )
        transactions.add(
            Transaction(
                "1234567890", System.currentTimeMillis(), 1000L,
                "Mandiri", "Origin Channel", "originName",
                "1234", "Emoney ", "Soto Padang", 0L, ""
            )
        )
        transactions.add(
            Transaction(
                "1234567890", System.currentTimeMillis(), 1000L,
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
