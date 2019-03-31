package com.example.benjo.swecar.utils

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.benjo.swecar.MainActivity
import com.example.benjo.swecar.MainViewModel
import com.example.benjo.swecar.data.network.model.SearchResponse
import com.google.gson.GsonBuilder


fun androidx.fragment.app.Fragment.string(id: Int): String = context!!.resources.getString(id)

fun androidx.fragment.app.Fragment.string(id: Int, variable : Int): String = context!!.resources.getString(id, variable)

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

fun androidx.fragment.app.Fragment.setupToolbar(toolbar: Toolbar) {
    mainActivity().setSupportActionBar(toolbar)
    mainActivity().supportActionBar?.title = null
    toolbar.setNavigationOnClickListener { mainActivity().onBackPressed() }
}

fun Fragment.viewModel() : MainViewModel {
    return ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
}

fun GsonBuilder.toSearchResponse(json : String) : SearchResponse {
    return this.create().fromJson(json, SearchResponse::class.java)
}