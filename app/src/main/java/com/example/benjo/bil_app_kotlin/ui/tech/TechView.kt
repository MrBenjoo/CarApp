package com.example.benjo.bil_app_kotlin.ui.tech


import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.R
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.MainActivity
import com.example.benjo.bil_app_kotlin.domain.Result
import com.example.benjo.bil_app_kotlin.ui.tab.TabsContract
import kotlinx.android.synthetic.main.fragment_base.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class TechView : BaseFragment(), TabsContract.ViewTech {
    private val TAG = "TechView"
    override lateinit var presenter: TabsContract.TechPresenter

    init {
        EventBus.getDefault().register(this)
    }

    override fun layoutId(): Int = R.layout.fragment_base

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = TechPresenter(SectionedRecyclerViewAdapter())
    }

    @Subscribe
    fun onEventResult(result : Result?) {
        presenter.updateTab(result)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        presenter.bind()
    }

    override fun setAdapter(adapter: SectionedRecyclerViewAdapter) {
        recyclerview_base.setHasFixedSize(true)
        recyclerview_base.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val result = (activity as MainActivity).resultCar1
        presenter.updateTab(result)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
