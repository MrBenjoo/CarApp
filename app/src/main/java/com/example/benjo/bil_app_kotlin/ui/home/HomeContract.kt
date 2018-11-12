package com.example.benjo.bil_app_kotlin.ui.home

import com.example.benjo.bil_app_kotlin.base.BasePresenter
import com.example.benjo.bil_app_kotlin.base.BaseView
import com.example.benjo.bil_app_kotlin.data.model.Result

interface HomeContract {

    interface View : BaseView<Presenter> {
        fun saveJsonAndOpenTabs(result: Result?)
    }

    interface Presenter : BasePresenter<View> {
        fun search(reg: String?)
    }

}