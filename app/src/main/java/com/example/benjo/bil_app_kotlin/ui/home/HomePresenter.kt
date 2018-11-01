package com.example.benjo.bil_app_kotlin.ui.home

import com.example.benjo.bil_app_kotlin.data.model.*
import com.example.benjo.bil_app_kotlin.utils.CommonUtils


class HomePresenter : HomeContract.Presenter {

    private val TAG = "HomePresenter"
    private lateinit var view : HomeContract.View



    override fun attachView(v: HomeContract.View) {
        this.view = v
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
        val homeSrchJson = CommonUtils().loadJSONFromAsset(view.getContext(), "bil_1.json")
        view.saveJsonAndOpenTabs(homeSrchJson) // sparar json i homeactivity
    }

}