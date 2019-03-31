package com.example.benjo.swecar.ui.tech

import com.example.benjo.swecar.base.BasePresenter
import com.example.benjo.swecar.base.BaseView
import com.example.benjo.swecar.data.network.model.SearchResponse
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter

interface TechContract {

    interface View : BaseView<Presenter> {
        fun setAdapter(adapter: SectionedRecyclerViewAdapter)
        fun getTechnicalTitle(): String
        fun getDimensionsTitle(): String
        fun getOtherTitle(): String
        fun showTextMalformedJson()
    }

    interface Presenter : BasePresenter<View> {
        fun bind()
        fun updateTab(searchResponse: SearchResponse?)
        fun updateTabTest()
    }

}