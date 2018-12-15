package com.example.benjo.bil_app_kotlin.data.network

import com.example.benjo.bil_app_kotlin.data.network.model.Result
import kotlinx.coroutines.Deferred
import retrofit2.Response

class CarService(private val api: BilUppgifterApi) {

    fun searchReg(reg: String?): Deferred<Response<Result>> {
        return api.search(reg)
    }

}