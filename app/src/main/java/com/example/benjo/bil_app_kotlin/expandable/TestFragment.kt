package com.example.benjo.bil_app_kotlin.expandable


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.list.Row
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_base.*


class TestFragment : Fragment() {

    private val arrayList = arrayListOf<Row>()
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

        /*for(j in 0..4) {
            val list = arrayListOf<Row>()
            for(i in 0..6) {
                list.add(Row(i.toString(), i.toString()))
            }
            sectionAdapter.addSection(ExpandableSection(j.toString(), list, sectionAdapter))
        }*/

        with(list) {
            setHasFixedSize(true)
            layoutManager = android.support.v7.widget.LinearLayoutManager(activity?.applicationContext)
            adapter = sectionAdapter
        }
    }

    fun updateList(viktInfo: ArrayList<Row>) {
        sectionAdapter.addSection(ExpandableSection("viktInfo", viktInfo, sectionAdapter))
        sectionAdapter.notifyDataSetChanged()
    }


}
