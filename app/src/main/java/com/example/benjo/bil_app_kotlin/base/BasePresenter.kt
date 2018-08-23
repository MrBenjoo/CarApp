package com.example.benjo.bil_app_kotlin.base

import com.example.benjo.bil_app_kotlin.network.json_parsing.Result

interface BasePresenter {


    fun update(jsonResult: String?)

    fun search(reg: String?)


}