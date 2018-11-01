package com.example.benjo.bil_app_kotlin.ui.basic


import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.benjo.bil_app_kotlin.adapters.AdapterBasicList
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.data.model.Row
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity
import com.example.benjo.bil_app_kotlin.ui.tab.TabsContract
import kotlinx.android.synthetic.main.fragment_base.*


class BasicFragment : BaseFragment(), TabsContract.ViewBasic {

    override fun layoutId(): Int = R.layout.fragment_base

    private val TAG = "BasicFragment"
    private lateinit var adapterBasicList: AdapterBasicList
    override lateinit var presenter: TabsContract.BasicPresenter


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HomeActivity)
            presenter = context.basicPresenter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterBasicList = AdapterBasicList(arrayListOf())
        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(activity?.applicationContext)
        list.adapter = adapterBasicList
        presenter.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        val jsonString = (activity as HomeActivity).firstJson//activity!!.intent.getStringExtra("jsonResult")
        val jsonResult = if (jsonString != null) presenter.fromJson(jsonString) else null
        presenter.updateTab(jsonResult)
    }

    override fun updateList(list: ArrayList<Row>) {
        adapterBasicList.setList(list)
    }

}
