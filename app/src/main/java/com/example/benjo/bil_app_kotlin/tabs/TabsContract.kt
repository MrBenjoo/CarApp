package com.example.benjo.bil_app_kotlin.tabs


import com.example.benjo.bil_app_kotlin.base.BasePresenter
import com.example.benjo.bil_app_kotlin.base.BaseView

import com.example.benjo.bil_app_kotlin.data.model.Result
import com.example.benjo.bil_app_kotlin.data.model.Row
import retrofit2.Response

interface TabsContract {


    /* Tab Related */
    interface ViewTabs : BaseView<TabsPresenter> {
        fun showText(textID: Int)
    }

    interface TabsPresenter {
        fun saveToDatabase(vin: Int, jsonResponse: String) : Boolean
        fun search(reg: String?): Response<Result>?
        fun validateResponse(response : Response<Result>?) : Result?
    }
    /* ----------- */


    /* Technical tab related */
    interface ViewTech : BaseView<TechPresenter> {
        fun updateList(title: String, list: ArrayList<Row>)
    }

    interface TechPresenter : BasePresenter<ViewTech>, Presenter
    /* -------------------- */


    /* Basic Tab related */
    interface ViewBasic : BaseView<BasicPresenter> {
        fun updateList(list: ArrayList<Row>)
    }

    interface BasicPresenter : BasePresenter<ViewBasic>, Presenter
    /* ------------------*/


    interface Presenter {
        fun updateTab(response: Result?)
        fun fromJson(json: String): Result
    }



}