package com.example.benjo.swecar.servicelocator

import android.util.Log
import com.example.benjo.swecar.App
import com.example.benjo.swecar.data.network.ApiHelper
import com.example.benjo.swecar.data.network.ApiInterface
import com.example.benjo.swecar.data.network.UrlManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * This class is used to define the implementation of the Api Service, separate of the
 * class implementing it.
 */
object CarServiceLocator {
    const val TAG = "CarServiceLocator"
    private var apiHelper: ApiHelper? = null

    fun provideCarService(): ApiHelper {
        if (apiHelper == null) {
            Log.d(TAG, "provideCarService --> creating new instance")
            apiHelper = ApiHelper(provideRetrofit().create(ApiInterface::class.java))
        }
        return apiHelper!!
    }


    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(UrlManager.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(provideOkHttpClient())
                .build()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .cache(provideCache())
                .addNetworkInterceptor(provideCacheInterceptor())
                .build()
    }

    private fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            // re-write response header to force use of cache
            val cacheControl = CacheControl.Builder()
                    .maxAge(1, TimeUnit.DAYS)
                    .build()

            response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build()
        }
    }

    private fun provideCache(): Cache? {
        var cache: Cache? = null

        try {
            cache = Cache(File(App.getContext().cacheDir, "http-cache"), 10 * 1024 * 1024)
            Log.d("ApiInterface", "Created cache.")
        } catch (exception: Exception) {
            Log.d("ApiInterface", "Could not create Cache!")
        }
        return cache
    }


}