package com.example.benjo.bil_app_kotlin.sections.tech


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.benjo.bil_app_kotlin.tabview.MainActivity
import com.example.benjo.bil_app_kotlin.sections.Row
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.base.Contract
import kotlinx.android.synthetic.main.fragment_base.*


class FragmentTech : BaseFragment(), Contract.ViewTech {
    private val TAG = "FragmentTech"
    private lateinit var sectionAdapter: SectionedRecyclerViewAdapter
    override lateinit var presenter: Contract.Presenter


    override fun getContext(): Context = activity!!.applicationContext

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sectionAdapter = SectionedRecyclerViewAdapter()
        list.adapter = sectionAdapter
    }


    override fun onResume() {
        super.onResume()
        val result = activity!!.intent.getStringExtra("technical")
        presenter = (activity as MainActivity).presenterTech
        if (result != null) {
            Log.d(TAG, "onResume -- result is NOT null")
            Log.d(TAG, result)
            presenter.update(result)
        }
    }

    override fun updateList(title: String, list: ArrayList<Row>) {
        sectionAdapter.addSection(ExpandableSection(title, list, sectionAdapter))
        sectionAdapter.notifyDataSetChanged()
    }
}
