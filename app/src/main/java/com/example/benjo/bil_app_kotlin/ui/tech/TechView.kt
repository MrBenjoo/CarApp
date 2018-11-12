package com.example.benjo.bil_app_kotlin.ui.tech


import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.R
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity
import com.example.benjo.bil_app_kotlin.ui.tab.TabsContract
import kotlinx.android.synthetic.main.fragment_base.*


class TechView : BaseFragment(), TabsContract.ViewTech {
    private val TAG = "TechView"
    override lateinit var presenter: TabsContract.TechPresenter

    override fun layoutId(): Int = R.layout.fragment_base

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HomeActivity)
            presenter = context.techPresenter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        presenter.bind()
    }

    override fun setAdapter(adapter: SectionedRecyclerViewAdapter) {
        list.setHasFixedSize(true)
        list.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val result = (activity as HomeActivity).resultCar1
        presenter.updateTab(result)
    }


}
