package com.example.benjo.bil_app_kotlin

import android.content.Context
import com.example.benjo.bil_app_kotlin.network.Connectivity
import com.example.benjo.bil_app_kotlin.network.json_parsing.Result
import com.example.benjo.bil_app_kotlin.network.retrofit.Lambda
import com.example.benjo.bil_app_kotlin.network.retrofit.SearchRegProvider
import retrofit2.Response

class MainPresenter(val context : Context){


    fun search(reg: String?): Response<Result>? {
        var mResponse: Response<Result>? = null
        val connected = Connectivity(context).isConnected()
        if(connected) {
            SearchRegProvider
                    .provideSearchReg()
                    .searchReg(reg)
                    .enqueue(Lambda().callback { throwable, response ->
                        throwable.let { /*onFailure(throwable)*/ }
                        response.let {
                            mResponse = response
                        }
                    })
        }

        return mResponse
    }
}