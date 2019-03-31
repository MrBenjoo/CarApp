package com.example.benjo.swecar.ui.basic

import com.example.benjo.swecar.base.BasePresenter
import com.example.benjo.swecar.base.BaseView
import com.example.benjo.swecar.data.network.model.SearchResponse

interface BasicContract {

    interface View : BaseView<Presenter> {
        fun setAdapter(adapter : BasicAdapter)
        fun showTextMalformedJson()
    }

    interface Presenter : BasePresenter<View> {
        fun bind()
        fun updateTab(searchResponse: SearchResponse?)
        fun updateTabTest()
    }

}