package com.example.benjo.bil_app_kotlin.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectivityStatus(private val context: Context) {

    fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

}