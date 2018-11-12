package com.example.benjo.bil_app_kotlin.domain

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Lambda {

    fun <T> callback(fn: (Throwable?, Response<T>?) -> Unit): Callback<T> {
        return object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) = fn(null, response)
            override fun onFailure(call: Call<T>, t: Throwable) = fn(t, null)
        }
    }

}