package com.example.benjo.bil_app_kotlin.home

import com.example.benjo.bil_app_kotlin.base.BasePresenter
import com.example.benjo.bil_app_kotlin.base.BaseView

interface HomeContract {

    interface View : BaseView<Presenter> {
        //fun updateView(list: ArrayList<Row>)
    }

    interface Presenter : BasePresenter<View> {
        fun search(reg: String?)
    }

}