package com.example.benjo.bil_app_kotlin.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.base.Contract
import kotlinx.android.synthetic.main.search_view.*


class HomeActivity : AppCompatActivity(), SearchView.OnQueryTextListener, Contract.ViewHome {
    override lateinit var presenter: Contract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initPresenter()
        search_view.setOnQueryTextListener(this)
    }

    private fun initPresenter() {
        presenter = HomePresenter(this,this)
    }

    override fun showProgess() {

    }

    override fun hideProgress() {

    }

    override fun showErrorHTTP() {

    }

    override fun getContext(): Context = this

    override fun onQueryTextSubmit(query: String?): Boolean = when(query?.length) {
        in 2..7 -> {
            search_view.onActionViewCollapsed()
            presenter.search(query?.trim())
            true
        }
        else -> {
            //showText(resources.getString(R.string.error_reg_limit))
            false
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, HomeActivity::class.java)
    }

}
