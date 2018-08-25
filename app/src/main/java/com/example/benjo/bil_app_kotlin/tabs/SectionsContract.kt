package com.example.benjo.bil_app_kotlin.tabs


import com.example.benjo.bil_app_kotlin.base.BasePresenter
import com.example.benjo.bil_app_kotlin.base.BaseView

import com.example.benjo.bil_app_kotlin.network.json.Result
import retrofit2.Response

interface SectionsContract {

    interface ViewTech : BaseView<Presenter> {
        fun updateList(title: String, list: ArrayList<Row>)
    }

    interface ViewBasic : BaseView<Presenter> {
        fun updateList(list: ArrayList<Row>)
    }

    interface TechPresenter : BasePresenter<ViewTech>, Presenter

    interface BasicPresenter : BasePresenter<ViewBasic>, Presenter

    interface Presenter {
        fun updateTab(response: Response<Result>?)
        fun update(jsonResult: String?)
        fun getJson() : Result?
    }

}