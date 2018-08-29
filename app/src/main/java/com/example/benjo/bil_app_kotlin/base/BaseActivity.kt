package com.example.benjo.bil_app_kotlin.base


import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.View
import com.example.benjo.bil_app_kotlin.R

import com.example.benjo.bil_app_kotlin.tabs.TabsContract
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity : AppCompatActivity(), SearchView.OnQueryTextListener, TabsContract.ViewTabs {
    lateinit var presenterBasic: TabsContract.Presenter
    lateinit var presenterTech: TabsContract.Presenter
    override lateinit var presenter: TabsContract.TabsPresenter


    override fun onQueryTextSubmit(query: String?) = when (query?.length) {
        in 2..7 -> {
            search_view.onActionViewCollapsed()
            val response = presenter.search(query?.trim())
            presenterBasic.updateTab(response)
            presenterTech.updateTab(response)
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
