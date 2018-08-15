package com.example.benjo.bil_app_kotlin.home

import com.example.benjo.bil_app_kotlin.base.BaseView
import com.example.benjo.bil_app_kotlin.sections.Row

interface HomeContract {

    interface View : BaseView<Presenter> {
        //fun updateView(list: ArrayList<Row>)
    }

    interface Presenter : BasePresenter {
        fun search(reg: String?)
    }

    interface BasePresenter
}