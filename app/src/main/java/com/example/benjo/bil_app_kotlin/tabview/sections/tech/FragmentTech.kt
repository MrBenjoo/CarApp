package com.example.benjo.bil_app_kotlin.tabview.sections.tech


import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.tabview.TabsActivity
import com.example.benjo.bil_app_kotlin.tabview.sections.Row
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.base.Contract
import com.example.benjo.bil_app_kotlin.tabview.sections.SectionsContract
import kotlinx.android.synthetic.main.fragment_base.*


class FragmentTech : BaseFragment(), SectionsContract.ViewTech {
    private val TAG = "FragmentTech"
    private lateinit var sectionAdapter: SectionedRecyclerViewAdapter
    override lateinit var presenter: SectionsContract.Presenter


    override fun getContext(): Context = activity!!.applicationContext

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = (context as TabsActivity).presenterTech
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sectionAdapter = SectionedRecyclerViewAdapter()
        list.adapter = sectionAdapter
    }


    override fun onResume() {
        super.onResume()
        val jsonResult = activity!!.intent.getStringExtra("jsonResult")
        if (jsonResult != null) {
            presenter.update(jsonResult)
        }
    }

    override fun updateList(title: String, list: ArrayList<Row>) {
        sectionAdapter.addSection(ExpandableSection(title, list, sectionAdapter))
        sectionAdapter.notifyDataSetChanged()
    }
}
