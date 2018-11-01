package com.example.benjo.bil_app_kotlin.ui.tech


import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.adapters.AdapterExpandableList
//import com.example.benjo.bil_app_kotlin.tabs.TabsActivity
import com.example.benjo.bil_app_kotlin.data.model.Row
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity
import com.example.benjo.bil_app_kotlin.ui.tab.TabsContract
import kotlinx.android.synthetic.main.fragment_base.*


class TechFragment : BaseFragment(), TabsContract.ViewTech {
    private val TAG = "TechFragment"
    private lateinit var sectionAdapter: SectionedRecyclerViewAdapter
    override lateinit var presenter: TabsContract.TechPresenter


    override fun layoutId(): Int = R.layout.fragment_base

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HomeActivity)
            presenter = context.techPresenter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sectionAdapter = SectionedRecyclerViewAdapter()
        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(activity?.applicationContext)
        list.adapter = sectionAdapter
        presenter.attachView(this)
    }


    override fun onResume() {
        super.onResume()
        if(sectionAdapter.itemCount < 1) {
            val jsonString = (activity as HomeActivity).firstJson
            val jsonResult = if (jsonString != null) presenter.fromJson(jsonString) else null

            presenter.updateTab(jsonResult)
        }
    }

    override fun updateList(title: String, list: ArrayList<Row>) {
        sectionAdapter.addSection(AdapterExpandableList(title, list, sectionAdapter))
        sectionAdapter.notifyDataSetChanged()
    }
}
