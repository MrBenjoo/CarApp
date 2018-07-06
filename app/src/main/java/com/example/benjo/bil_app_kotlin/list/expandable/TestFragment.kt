package com.example.benjo.bil_app_kotlin.list.expandable


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.list.Row
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_base.*


class TestFragment : Fragment() {
    private lateinit var sectionAdapter: SectionedRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initList()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initList() {
        sectionAdapter = SectionedRecyclerViewAdapter()
        with(list) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = sectionAdapter
        }
    }

    fun updateList(title: String, list: ArrayList<Row>) {
        sectionAdapter.addSection(ExpandableSection(title, list, sectionAdapter))
        sectionAdapter.notifyDataSetChanged()
    }


}
