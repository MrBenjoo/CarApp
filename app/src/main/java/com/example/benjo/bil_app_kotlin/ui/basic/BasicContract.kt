package com.example.benjo.bil_app_kotlin.ui.basic

import com.example.benjo.bil_app_kotlin.base.BasePresenter
import com.example.benjo.bil_app_kotlin.base.BaseView
import com.example.benjo.bil_app_kotlin.data.network.model.SearchResponse

interface BasicContract {

    interface View : BaseView<Presenter> {
        fun setAdapter(adapter : BasicAdapter)
        fun showTextMalformedJson()
    }

    interface Presenter : BasePresenter<View> {
        fun bind()
        fun updateTab(searchResponse: SearchResponse?)
    }

}