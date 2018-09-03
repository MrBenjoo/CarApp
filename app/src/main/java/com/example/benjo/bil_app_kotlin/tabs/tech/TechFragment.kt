package com.example.benjo.bil_app_kotlin.tabs.tech


import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.adapters.AdapterExpandableList
import com.example.benjo.bil_app_kotlin.tabs.TabsActivity
import com.example.benjo.bil_app_kotlin.tabs.Row
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.tabs.TabsContract
import kotlinx.android.synthetic.main.fragment_base.*


class TechFragment : BaseFragment(), TabsContract.ViewTech {
    private val TAG = "TechFragment"
    private lateinit var sectionAdapter: SectionedRecyclerViewAdapter
    override lateinit var presenter: TabsContract.TechPresenter


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is TabsActivity)
            presenter = context.techPresenter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sectionAdapter = SectionedRecyclerViewAdapter()
        list.adapter = sectionAdapter
        presenter.attachView(this)
    }


    override fun onResume() {
        super.onResume()
        val jsonResult = activity!!.intent.getStringExtra("jsonResult")
        if (jsonResult != null) presenter.update(jsonResult)
    }

    override fun updateList(title: String, list: ArrayList<Row>) {
        sectionAdapter.addSection(AdapterExpandableList(title, list, sectionAdapter))
        sectionAdapter.notifyDataSetChanged()
    }
}
