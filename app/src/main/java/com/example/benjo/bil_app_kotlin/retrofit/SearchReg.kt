package com.example.benjo.bil_app_kotlin.retrofit

import com.example.benjo.bil_app_kotlin.data.model.Result
import retrofit2.Call

class SearchReg(val apiService: BilUppgifterApi) {

    fun searchReg(reg: String?): Call<Result> {
        return apiService.search(reg)
    }

}