package com.example.benjo.bil_app_kotlin.tabs


import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.ImageView
import com.example.benjo.bil_app_kotlin.Constants.Companion.TITLE_TAB_1
import com.example.benjo.bil_app_kotlin.Constants.Companion.TITLE_TAB_2
import com.example.benjo.bil_app_kotlin.Constants.Companion.TITLE_TAB_3
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.adapters.AdapterTabsPage
import com.example.benjo.bil_app_kotlin.base.BaseActivity
import com.example.benjo.bil_app_kotlin.home.HomeActivity
import com.example.benjo.bil_app_kotlin.tabs.tech.TechFragment
import com.example.benjo.bil_app_kotlin.tabs.tech.TechPresenter
import com.example.benjo.bil_app_kotlin.tabs.basic.BasicFragment
import com.example.benjo.bil_app_kotlin.tabs.basic.BasicPresenter
import com.example.benjo.bil_app_kotlin.network.ConnectivityHandler
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.runBlocking


class TabsActivity : BaseActivity() {
    private val TAG = "TabsActivity"
    private lateinit var adapterTabsPage: AdapterTabsPage
    private lateinit var receiver: ConnectivityHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        initTabs()
        initBroadcast()
        initPresenter()
        initSaveListener()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        search_view.setOnQueryTextListener(this)
    }

    private fun initTabs() {
        tabs.setupWithViewPager(container)
        adapterTabsPage = AdapterTabsPage(supportFragmentManager)
        container.adapter = adapterTabsPage
    }

    private fun initBroadcast() {
        receiver = ConnectivityHandler(this)
        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    /* TODO flytta till sectionspageadapter? */
    private fun initPresenter() {
        val fragmentTab1 = BasicFragment()
        val fragmentTab2 = TechFragment()
        val fragmentTab3 = BasicFragment()
        with(adapterTabsPage) {
            addFragment(fragmentTab1, TITLE_TAB_1)
            addFragment(fragmentTab2, TITLE_TAB_2)
            addFragment(fragmentTab3, TITLE_TAB_3)
        }
        presenterBasic = BasicPresenter(fragmentTab1)
        presenterTech = TechPresenter(fragmentTab2)
        presenter = TabsPresenter(this)
    }

    private fun initSaveListener() {
        img_save.setOnClickListener {
            val jsonResponse = presenterBasic.getJson()
            val vin = jsonResponse?.carInfo?.attributes?.vin!!.toInt()
            val json = GsonBuilder().create().toJson(jsonResponse)
            val saved = runBlocking { presenter.saveToDatabase(vin, json) }
            if(saved) showText(R.string.view_tabs_car_saved)
            else showText(R.string.view_tabs_car_not_saved)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(HomeActivity.newIntent(this))
        finish()
        return true
    }

    fun internetOff() {
        showText(R.string.error_no_internet)
        info_img_wifi_off.visibility = ImageView.VISIBLE
    }

    fun internetOn() {
        info_img_wifi_off.visibility = ImageView.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    override fun getContext(): Context = applicationContext

    override fun showText(textID: Int) {
        Snackbar.make(tabs_root_view, textID, Snackbar.LENGTH_LONG).show()
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, TabsActivity::class.java)
    }

}
