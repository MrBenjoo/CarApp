package com.example.benjo.bil_app_kotlin.domain

import kotlinx.coroutines.Deferred
import retrofit2.Response

class SearchReg(val apiService: BilUppgifterApi) {

    fun searchReg(reg: String?): Deferred<Response<Result>> {
        return apiService.search(reg)
    }

}