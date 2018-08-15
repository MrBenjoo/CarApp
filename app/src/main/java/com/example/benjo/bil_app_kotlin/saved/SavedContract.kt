package com.example.benjo.bil_app_kotlin.saved

import com.example.benjo.bil_app_kotlin.base.BaseView
import com.example.benjo.bil_app_kotlin.sections.Row

interface SavedContract {

    interface View : BaseView<Presenter> {
        fun updateView(list: ArrayList<Row>)
    }

    interface Presenter : BasePresenter {
        fun loadSavedCars()
        fun attachView(view : View)
        fun showSavedCars(list: ArrayList<Row>)
    }

    interface BasePresenter

}