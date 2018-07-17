package com.example.benjo.bil_app_kotlin.base

import com.example.benjo.bil_app_kotlin.sections.Row

interface Contract {

    interface View : BaseView<Presenter> {
        fun updateList(list: ArrayList<Row>)
    }

    interface ViewTech : BaseView<Presenter> {
        fun updateList(title: String, list: ArrayList<Row>)
    }

    interface Presenter : BasePresenter
}