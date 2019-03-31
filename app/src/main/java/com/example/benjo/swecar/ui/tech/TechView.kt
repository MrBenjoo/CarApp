package com.example.benjo.swecar.ui.tech


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.example.benjo.swecar.R
import com.example.benjo.swecar.base.BaseFragment
import com.example.benjo.swecar.data.network.model.SearchResponse
import com.example.benjo.swecar.MainViewModel
import com.example.benjo.swecar.utils.showText
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_base.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class TechView : BaseFragment(), TechContract.View {
    private val TAG = "TechView"
    override lateinit var presenter: TechContract.Presenter

    override fun layoutId(): Int = R.layout.fragment_base

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = TechPresenter(SectionedRecyclerViewAdapter(), ViewModelProviders.of(activity!!).get(MainViewModel::class.java))
    }

    /**
     * Gets called from TabsPresenter when searching for a new registration number and
     * the app is not in a compare mode state.
     */
    @Subscribe
    fun onEventResult(searchResponse: SearchResponse?) {
        presenter.updateTab(searchResponse)
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
        //presenter.updateTab(mainActivity().searchResponseCar1)
        presenter.updateTabTest()
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

    override fun showTextMalformedJson() {
        showText(R.string.error_malformed_json)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}
