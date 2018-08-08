package com.example.benjo.bil_app_kotlin.base


import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.View
import com.example.benjo.bil_app_kotlin.R
import kotlinx.android.synthetic.main.search_view.*

abstract class BaseActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    lateinit var presenterBasic: Contract.Presenter
    lateinit var presenterTech: Contract.Presenter


    override fun onQueryTextSubmit(query: String?) = when(query?.length) {
        in 2..7 -> {
            search_view.onActionViewCollapsed()
            presenterBasic.search(query?.trim())
            //presenterTech.search(query?.trim())
            true
        }
        else -> {
            showText(resources.getString(R.string.error_reg_limit))
            false
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    fun showText(text: String?) {
        Snackbar.make(findViewById<View>(android.R.id.content), text.toString(), Snackbar.LENGTH_LONG).show()
    }

}
