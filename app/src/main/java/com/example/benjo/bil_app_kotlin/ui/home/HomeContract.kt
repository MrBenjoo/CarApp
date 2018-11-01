package com.example.benjo.bil_app_kotlin.ui.home

import com.example.benjo.bil_app_kotlin.base.BasePresenter
import com.example.benjo.bil_app_kotlin.base.BaseView

interface HomeContract {

    interface View : BaseView<Presenter> {
        fun saveJsonAndOpenTabs(json: String)
    }

    interface Presenter : BasePresenter<View> {
        fun search(reg: String?)
    }

}