package com.example.benjo.bil_app_kotlin.network.retrofit

import com.example.benjo.bil_app_kotlin.network.json.Result
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BilUppgifterApi {

    @GET("vehicle/regno/{reg}/")
    fun search(@Path("reg") reg: String?, @Query("api_token") token: String = "o4SOIaXVfGImZgQj0bq6gozpgmHf3JKJZbwIUOBPWdH9hr1yxRJ9zkIgylSn"): Call<Result>

    class Factory {
        companion object {
            fun create(): BilUppgifterApi {
                return Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://api.biluppgifter.se/api/v1/")
                        .build()
                        .create(BilUppgifterApi::class.java)
            }
        }
    }

}