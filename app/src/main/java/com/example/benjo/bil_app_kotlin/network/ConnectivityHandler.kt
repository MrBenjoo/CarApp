package com.example.benjo.bil_app_kotlin.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.example.benjo.bil_app_kotlin.tabs.TabsActivity

class ConnectivityHandler(val activity: TabsActivity) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) = when (intent?.action) {
        ConnectivityManager.CONNECTIVITY_ACTION ->
            with(activity) {
                if (ConnectivityStatus(applicationContext).isConnected()) {
                    internetOn()
                } else {
                    internetOff()
                }
            }
        else -> Unit
    }

}