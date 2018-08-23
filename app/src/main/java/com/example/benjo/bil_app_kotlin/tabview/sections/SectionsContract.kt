package com.example.benjo.bil_app_kotlin.tabview.sections

import com.example.benjo.bil_app_kotlin.base.BaseView
import com.example.benjo.bil_app_kotlin.home.HomeContract
import com.example.benjo.bil_app_kotlin.network.json_parsing.Result
import retrofit2.Response

interface SectionsContract {

    interface ViewTech : BaseView<SectionsContract.Presenter> {
        fun updateList(title: String, list: ArrayList<Row>)
    }

    interface ViewBasic : BaseView<SectionsContract.Presenter> {
        fun updateList(list: ArrayList<Row>)
    }

    interface Presenter : BasePresenter {
        fun updateTab(response: Response<Result>?)
        fun update(jsonResult: String?)
        fun getJson() : Result?
    }

    interface BasePresenter
}