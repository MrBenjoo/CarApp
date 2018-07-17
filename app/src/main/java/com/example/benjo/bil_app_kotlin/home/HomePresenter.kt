package com.example.benjo.bil_app_kotlin.home

import com.example.benjo.bil_app_kotlin.base.Contract
import com.example.benjo.bil_app_kotlin.network.json_parsing.Result

class HomePresenter(val view: Contract.View) : Contract.Presenter {


    override fun updateTab(map: HashMap<String, String?>?) {

    }

    override fun processResponse(response: Result?) {

    }

    override fun search(reg: String?) {

    }

    init {
        view.presenter = this
    }

}