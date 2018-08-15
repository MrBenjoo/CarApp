package com.example.benjo.bil_app_kotlin.tabview


import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.ImageView
import com.example.benjo.bil_app_kotlin.Constants.Companion.TITLE_TAB_1
import com.example.benjo.bil_app_kotlin.Constants.Companion.TITLE_TAB_2
import com.example.benjo.bil_app_kotlin.Constants.Companion.TITLE_TAB_3
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.base.BaseActivity
import com.example.benjo.bil_app_kotlin.home.HomeActivity
import com.example.benjo.bil_app_kotlin.sections.tech.FragmentTech
import com.example.benjo.bil_app_kotlin.sections.tech.PresenterTech
import com.example.benjo.bil_app_kotlin.sections.basic.FragmentBasic
import com.example.benjo.bil_app_kotlin.sections.basic.PresenterBasic
import com.example.benjo.bil_app_kotlin.network.MyReceiver
import com.example.benjo.bil_app_kotlin.network.json_parsing.BasicInfo
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_view.*


class MainActivity : BaseActivity() {
    private val TAG = "MainActivity"
    private lateinit var sectionsPageAdapter: SectionsPageAdapter
    private lateinit var receiver: MyReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        initTabs()
        initBroadcast()
        initPresenter()
        initListener()
    }


    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        search_view.setOnQueryTextListener(this)
    }

    private fun initTabs() {
        tabs.setupWithViewPager(container)
        sectionsPageAdapter = SectionsPageAdapter(supportFragmentManager)
        container.adapter = sectionsPageAdapter
    }

    private fun initBroadcast() {
        receiver = MyReceiver(this)
        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun initPresenter() {
        val fragmentTab1 = FragmentBasic()
        val fragmentTab2 = FragmentTech()
        val fragmentTab3 = FragmentBasic()
        with(sectionsPageAdapter) {
            addFragment(fragmentTab1, TITLE_TAB_1)
            addFragment(fragmentTab2, TITLE_TAB_2)
            addFragment(fragmentTab3, TITLE_TAB_3)
        }
        presenterBasic = PresenterBasic(fragmentTab1)
        presenterTech = PresenterTech(fragmentTab2)
    }

    private fun initListener() {
        img_save.setOnClickListener {
            val basic = BasicInfo("Volvo S80", "S80", "I trafik", "Svart", "Personbil", "2003", "2003")
            val gson = GsonBuilder().create()
            val jsonBasic = gson.toJson(basic)
            MainPresenter(applicationContext).saveToDatabase(jsonBasic) }
    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(HomeActivity.newIntent(this))
        finish()
        return true
    }

    fun internetOff() {
        showText(resources.getString(R.string.error_no_internet))
        info_img_wifi_off.visibility = ImageView.VISIBLE
    }

    fun internetOn() {
        info_img_wifi_off.visibility = ImageView.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

}
