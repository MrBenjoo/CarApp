package com.example.benjo.bil_app_kotlin.list.expandable


import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.list.model.Row
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import com.example.benjo.bil_app_kotlin.BaseFragment
import kotlinx.android.synthetic.main.fragment_base.*


class ExpandableFragment : BaseFragment() {
    private lateinit var sectionAdapter: SectionedRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sectionAdapter = SectionedRecyclerViewAdapter()
        list.adapter = sectionAdapter
    }

    fun updateList(title: String, list: ArrayList<Row>) {
        sectionAdapter.addSection(ExpandableSection(title, list, sectionAdapter))
        sectionAdapter.notifyDataSetChanged()
    }

}
