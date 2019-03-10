package com.example.benjo.bil_app_kotlin.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.example.benjo.bil_app_kotlin.App
import com.example.benjo.bil_app_kotlin.ui.tab.TabsContract

class ConnectivityHandler(private val view: TabsContract.View) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) = when (intent?.action) {
        ConnectivityManager.CONNECTIVITY_ACTION ->
            with(view) {
                if (CommonUtils().isConnected(App.getContext())) {
                    showInternetOn()
                } else {
                    showInternetOff()
                }
            }
        else -> Unit
    }

}