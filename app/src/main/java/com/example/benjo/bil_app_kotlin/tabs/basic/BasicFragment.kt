package com.example.benjo.bil_app_kotlin.tabs.basic


import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.adapters.AdapterBasicList
import com.example.benjo.bil_app_kotlin.tabs.TabsActivity
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.tabs.Row
import com.example.benjo.bil_app_kotlin.tabs.TabsContract
import kotlinx.android.synthetic.main.fragment_base.*


class BasicFragment : BaseFragment(), TabsContract.ViewBasic {
    private val TAG = "BasicFragment"
    private lateinit var adapterBasicList: AdapterBasicList
    override lateinit var presenter: TabsContract.Presenter


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is TabsActivity)
            presenter = context.presenterBasic
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterBasicList = AdapterBasicList(arrayListOf<Row>())
        list.adapter = adapterBasicList
    }

    override fun onResume() {
        super.onResume()
        val jsonResult = activity!!.intent.getStringExtra("jsonResult")
        if (jsonResult != null) presenter.update(jsonResult)
    }

    override fun updateList(list: ArrayList<Row>) {
        adapterBasicList.setList(list)
    }

}
