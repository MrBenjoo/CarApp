package com.example.benjo.bil_app_kotlin.domain

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BilUppgifterApi {

    @GET(UrlManager.VEHICLE)
    fun search(@Path("reg") reg: String?, @Query("api_token") token: String = UrlManager.API_TOKEN): Deferred<Response<Result>>

    class Factory {
        companion object {
            fun create(): BilUppgifterApi {
                return Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(CoroutineCallAdapterFactory())
                        .baseUrl(UrlManager.API_HOST)
                        .build()
                        .create(BilUppgifterApi::class.java)
            }
        }
    }

}