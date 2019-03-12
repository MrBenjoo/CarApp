package com.example.benjo.bil_app_kotlin.utils

import android.content.BroadcastReceiver
import android.content.IntentFilter
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import android.view.View
import androidx.navigation.findNavController
import com.example.benjo.bil_app_kotlin.MainActivity


fun androidx.fragment.app.Fragment.string(id: Int): String = context!!.resources.getString(id)

fun androidx.fragment.app.Fragment.navigate(id: Int) {
    view!!.findNavController().navigate(id)
}

fun androidx.fragment.app.Fragment.registerReceiver(receiver: BroadcastReceiver, filter: IntentFilter) {
    (activity as MainActivity).registerReceiver(receiver, filter)
}

fun androidx.fragment.app.Fragment.unregisterReceiver(receiver: BroadcastReceiver) {
    (activity as MainActivity).unregisterReceiver(receiver)
}

fun androidx.fragment.app.Fragment.mainActivity(): MainActivity = (activity as MainActivity)

fun androidx.fragment.app.Fragment.hideView(view: View) {
    view.visibility = View.INVISIBLE
}

fun androidx.fragment.app.Fragment.showView(view: View) {
    view.visibility = View.VISIBLE
}

fun androidx.fragment.app.Fragment.showText(textID: Int) {
    com.google.android.material.snackbar.Snackbar.make(
            mainActivity().findViewById<View>(android.R.id.content),
            textID,
            com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
            .show()
}

fun androidx.fragment.app.Fragment.showText(text: String) {
    com.google.android.material.snackbar.Snackbar.make(
            mainActivity().findViewById<View>(android.R.id.content),
            text,
            com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
            .show()
}

fun androidx.fragment.app.Fragment.setupToolbar(toolbar: Toolbar, listener: Toolbar.OnMenuItemClickListener) {
    mainActivity().setSupportActionBar(toolbar)
    mainActivity().supportActionBar?.title = null
    toolbar.setNavigationOnClickListener { mainActivity().onBackPressed() }
    toolbar.setOnMenuItemClickListener(listener)
}