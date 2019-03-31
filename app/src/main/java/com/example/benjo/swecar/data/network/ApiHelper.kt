package com.example.benjo.swecar.data.network

import com.example.benjo.swecar.data.network.model.SearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

class ApiHelper(private val api: ApiInterface) {

    fun searchRegAsync(reg: String?): Deferred<Response<SearchResponse>> = api.searchAsync(reg)

}