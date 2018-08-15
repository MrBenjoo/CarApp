package com.example.benjo.bil_app_kotlin.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.example.benjo.bil_app_kotlin.tabview.MainActivity

class MyReceiver(val activity: MainActivity) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) = when (intent?.action) {
        ConnectivityManager.CONNECTIVITY_ACTION ->
            with(activity) {
                if (Connectivity(applicationContext).isConnected()) {
                    internetOn()
                } else {
                    internetOff()
                }
            }
        else -> Unit
    }

}