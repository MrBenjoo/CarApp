package com.example.benjo.bil_app_kotlin.ui.basic

import com.example.benjo.bil_app_kotlin.base.BasePresenter
import com.example.benjo.bil_app_kotlin.base.BaseView
import com.example.benjo.bil_app_kotlin.data.network.model.SearchResponse

interface BasicContract {

    interface ViewBasic : BaseView<BasicPresenter> {
        fun setAdapter(adapter : BasicAdapter)
    }

    interface BasicPresenter : BasePresenter<ViewBasic> {
        fun bind()
        fun updateTab(searchResponse: SearchResponse?)
    }

}