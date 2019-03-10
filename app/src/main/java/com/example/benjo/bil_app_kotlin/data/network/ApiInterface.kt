package com.example.benjo.bil_app_kotlin.data.network

import com.example.benjo.bil_app_kotlin.data.network.model.SearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET(UrlManager.API_ENDPOINT)
    fun searchAsync(
            @Path("reg") reg: String?,
            @Query("api_token") token: String = UrlManager.API_TOKEN)
            : Deferred<Response<SearchResponse>>

}


