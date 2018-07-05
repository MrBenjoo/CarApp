package com.example.benjo.bil_app_kotlin.network.retrofit

import com.example.benjo.bil_app_kotlin.network.json_parsing.Result
import retrofit2.Call

class SearchReg(val apiService: BilUppgifterApi) {

    fun searchReg(reg: String?): Call<Result> {
        return apiService.search(reg)
    }

}