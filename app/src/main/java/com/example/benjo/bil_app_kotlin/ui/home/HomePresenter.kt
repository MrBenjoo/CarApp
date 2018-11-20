package com.example.benjo.bil_app_kotlin.ui.home

import com.example.benjo.bil_app_kotlin.domain.Result
import com.example.benjo.bil_app_kotlin.utils.CommonUtils
import com.google.gson.GsonBuilder


class HomePresenter : HomeContract.Presenter {

    private val TAG = "HomePresenter"
    private lateinit var view : HomeContract.View

    override fun attachView(view: HomeContract.View) {
        this.view = view
    }

    override fun search(reg: String?) {
        // view.showProgess()
        /*val response = TabsPresenter(view.getContext()).search(reg)
        if (response != null) {
            if (response.isSuccessful)
                processResponse(response.body())
            else
                view.showErrorHTTP()
        }*/
        processResponse(null) // test...
        //view.hideProgress()
    }

    private fun processResponse(body: Result?) {
        /* ------------------------------------------------------------- Används endast för att testa UI -------------------------------------------------------------*/
        val jsonCarOne = CommonUtils().loadJSONFromAsset(view.getContext(), "bil_1.json")
        val result = GsonBuilder().create().fromJson(jsonCarOne, Result::class.java)
        view.saveJsonAndOpenTabs(result) // sparar json i homeactivity
    }

}