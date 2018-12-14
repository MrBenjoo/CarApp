package com.example.benjo.bil_app_kotlin.ui.tech


import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.R
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.MainActivity
import com.example.benjo.bil_app_kotlin.data.network.model.Result
import kotlinx.android.synthetic.main.fragment_base.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class TechView : BaseFragment(), TechContract.ViewTech {
    private val TAG = "TechView"
    override lateinit var presenter: TechContract.TechPresenter

    override fun layoutId(): Int = R.layout.fragment_base

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = TechPresenter(SectionedRecyclerViewAdapter())
    }

    @Subscribe
    fun onEventResult(result: Result?) {
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

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onResume() {
        super.onResume()
        val result = (activity as MainActivity).resultCar1
        presenter.updateTab(result)
    }

    override fun getTechnicalTitle(): String {
        return context.resources?.getString(R.string.tabs_technical_title) ?: "Teknisk"
    }

    override fun getDimensionsTitle(): String {
        return context.resources?.getString(R.string.tabs_dimensions_title) ?: "Dimensioner"
    }

    override fun getOtherTitle(): String {
        return context.resources?.getString(R.string.tabs_other_title) ?: "Övrigt"
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
