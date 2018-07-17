package com.example.benjo.bil_app_kotlin.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.benjo.bil_app_kotlin.base.BaseActivity
import com.example.benjo.bil_app_kotlin.R
import kotlinx.android.synthetic.main.search_view.*

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        search_view.setOnQueryTextListener(this)
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, HomeActivity::class.java)
    }

}
