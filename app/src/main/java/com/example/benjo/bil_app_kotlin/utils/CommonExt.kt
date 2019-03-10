package com.example.benjo.bil_app_kotlin.utils

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.View
import androidx.navigation.findNavController
import com.example.benjo.bil_app_kotlin.MainActivity


fun Fragment.string(id: Int): String = context!!.resources.getString(id)

fun Fragment.navigate(id: Int) {
    view!!.findNavController().navigate(id)
}

fun Fragment.registerReceiver(receiver: BroadcastReceiver, filter: IntentFilter) {
    (activity as MainActivity).registerReceiver(receiver, filter)
}

fun Fragment.unregisterReceiver(receiver: BroadcastReceiver) {
    (activity as MainActivity).unregisterReceiver(receiver)
}

fun Fragment.mainActivity(): MainActivity = (activity as MainActivity)

fun Fragment.hideView(view: View) {
    view.visibility = View.INVISIBLE
}

fun Fragment.showView(view: View) {
    view.visibility = View.VISIBLE
}

fun Fragment.showText(textID: Int) {
    Snackbar.make(
            mainActivity().findViewById<View>(android.R.id.content),
            textID,
            Snackbar.LENGTH_LONG)
            .show()
}

fun Fragment.setupToolbar(toolbar: Toolbar, listener: Toolbar.OnMenuItemClickListener) {
    mainActivity().setSupportActionBar(toolbar)
    mainActivity().supportActionBar?.title = null
    toolbar.setNavigationOnClickListener { mainActivity().onBackPressed() }
    toolbar.setOnMenuItemClickListener(listener)
}