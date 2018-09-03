package com.example.benjo.bil_app_kotlin.tabs


import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.ImageView
import com.example.benjo.bil_app_kotlin.Constants
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.adapters.AdapterTabsPage
import com.example.benjo.bil_app_kotlin.home.HomeActivity
import com.example.benjo.bil_app_kotlin.tabs.tech.TechPresenter
import com.example.benjo.bil_app_kotlin.tabs.basic.BasicPresenter
import com.example.benjo.bil_app_kotlin.network.ConnectivityHandler
import com.example.benjo.bil_app_kotlin.network.json.Result
import com.example.benjo.bil_app_kotlin.tabs.basic.BasicFragment
import com.example.benjo.bil_app_kotlin.tabs.tech.TechFragment
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.runBlocking
import retrofit2.Response


class TabsActivity : AppCompatActivity(), SearchView.OnQueryTextListener, TabsContract.ViewTabs {
    private val TAG = "TabsActivity"
    private lateinit var adapterTabsPage: AdapterTabsPage
    private lateinit var receiver: ConnectivityHandler
    private var response: Response<Result>? = null
    lateinit var basicPresenter: TabsContract.BasicPresenter
    lateinit var techPresenter: TabsContract.TechPresenter
    override lateinit var presenter: TabsContract.TabsPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPresenters()
        initToolbar()
        initTabs()
        initBroadcast()
        img_save.setOnClickListener { onImgSaveClick() }
    }

    private fun initPresenters() {
        basicPresenter = BasicPresenter()
        techPresenter = TechPresenter()
        presenter = TabsPresenter(this)
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
        with(adapterTabsPage) {
            addFragment(BasicFragment(), Constants.TITLE_TAB_1)
            addFragment(TechFragment(), Constants.TITLE_TAB_2)
            addFragment(BasicFragment(), Constants.TITLE_TAB_3)
        }
    }

    private fun initBroadcast() {
        receiver = ConnectivityHandler(this)
        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun onImgSaveClick() {
        val newJson = response?.body()
        val result = newJson ?: intentJson()
        val vin = result?.carInfo?.attributes?.vin!!.toInt()
        val json = GsonBuilder().create().toJson(result)
        val saved = runBlocking { presenter.saveToDatabase(vin, json) }
        if (saved) showText(R.string.view_tabs_car_saved)
        else showText(R.string.view_tabs_car_not_saved)
    }

    private fun intentJson(): Result? {
        return GsonBuilder().create().fromJson(intent.getStringExtra("jsonResult"), Result::class.java)
    }

    override fun onQueryTextSubmit(query: String?) = when (query?.length) {
        in 2..7 -> {
            search_view.onActionViewCollapsed()
            response = presenter.search(query?.trim())
            basicPresenter.updateTab(response)
            techPresenter.updateTab(response)
            true
        }
        else -> {
            showText(resources.getString(R.string.error_reg_limit))
            false
        }
    }

    fun internetOff() {
        showText(R.string.error_no_internet)
        info_img_wifi_off.visibility = ImageView.VISIBLE
    }

    fun internetOn() {
        info_img_wifi_off.visibility = ImageView.INVISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(HomeActivity.newIntent(this))
        finish()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    override fun getContext(): Context = applicationContext

    override fun showText(textID: Int) {
        Snackbar.make(tabs_root_view, textID, Snackbar.LENGTH_LONG).show()
    }

    fun showText(text: String?) {
        Snackbar.make(findViewById<View>(android.R.id.content), text.toString(), Snackbar.LENGTH_LONG).show()
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, TabsActivity::class.java)
    }


}
