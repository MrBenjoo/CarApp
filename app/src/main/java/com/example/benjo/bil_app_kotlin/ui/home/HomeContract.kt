package com.example.benjo.bil_app_kotlin.ui.home

import com.example.benjo.bil_app_kotlin.base.BasePresenter
import com.example.benjo.bil_app_kotlin.base.BaseView
import com.example.benjo.bil_app_kotlin.domain.Result

interface HomeContract {

    interface View : BaseView<Presenter> {
        fun saveJsonAndOpenTabs(result: Result?)
        fun showResponseCode(code: Int)
    }

    interface Presenter : BasePresenter<View> {
        fun search(reg: String?)
    }

}