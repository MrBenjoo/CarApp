package com.example.benjo.bil_app_kotlin.base

import com.example.benjo.bil_app_kotlin.network.json_parsing.Result

interface BasePresenter {

    fun search(reg: String?)

    fun processResponse(response: Result?)

    fun updateTab(map: HashMap<String, String?>?)

}