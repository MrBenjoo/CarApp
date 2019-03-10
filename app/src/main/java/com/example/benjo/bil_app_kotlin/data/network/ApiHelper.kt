package com.example.benjo.bil_app_kotlin.data.network

import com.example.benjo.bil_app_kotlin.data.network.model.SearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

class ApiHelper(private val api: ApiInterface) {

    fun searchRegAsync(reg: String?): Deferred<Response<SearchResponse>> = api.searchAsync(reg)

}