package com.example.benjo.bil_app_kotlin.tabs


import com.example.benjo.bil_app_kotlin.base.BasePresenter
import com.example.benjo.bil_app_kotlin.base.BaseView

import com.example.benjo.bil_app_kotlin.network.json.Result
import retrofit2.Response

interface TabsContract {


    /* Tab Related */
    interface ViewTabs : BaseView<TabsPresenter> {
        fun showText(textID: Int)
    }

    interface TabsPresenter {
        suspend fun saveToDatabase(vin: Int, jsonResponse: String) : Boolean
        fun search(reg: String?): Response<Result>?
    }
    /* ----------- */


    /* Technical tab related */
    interface ViewTech : BaseView<Presenter> {
        fun updateList(title: String, list: ArrayList<Row>)
    }

    interface TechPresenter : BasePresenter<ViewTech>, Presenter
    /* -------------------- */


    /* Basic Tab related */
    interface ViewBasic : BaseView<Presenter> {
        fun updateList(list: ArrayList<Row>)
    }

    interface BasicPresenter : BasePresenter<ViewBasic>, Presenter
    /* ------------------*/


    interface Presenter {
        fun updateTab(response: Response<Result>?)
        fun update(jsonResult: String?)
        fun getJson() : Result?
    }



}