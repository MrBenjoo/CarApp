package com.example.benjo.bil_app_kotlin.ui.tab


import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.Navigation

import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.data.db.repository.CarRepositoryImpl
import com.example.benjo.bil_app_kotlin.data.db.CarDataBase
import com.example.benjo.bil_app_kotlin.data.network.model.Result
import com.example.benjo.bil_app_kotlin.MainActivity

import com.example.benjo.bil_app_kotlin.ui.basic.BasicView
import com.example.benjo.bil_app_kotlin.ui.tech.TechView
import com.example.benjo.bil_app_kotlin.utils.CommonUtils
import com.example.benjo.bil_app_kotlin.utils.ConnectivityHandler
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_tabs.*
import org.greenrobot.eventbus.EventBus
import com.example.benjo.bil_app_kotlin.R


class TabsView : BaseFragment(), SearchView.OnQueryTextListener, TabsContract.ViewTabs {
    private val TAG = "TabsView"

    override lateinit var presenter: TabsContract.TabsPresenter
    private lateinit var tabsAdapter: TabsAdapter
    private lateinit var receiver: ConnectivityHandler
    private lateinit var internetStatusMenuItem: MenuItem
    private lateinit var searchMenuItem: MenuItem
    private lateinit var compareMenuItem: MenuItem
    private lateinit var searchView: SearchView


    override fun layoutId(): Int = R.layout.fragment_tabs

    companion object {
        var isComparing = false
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = TabsPresenter(this, CarRepositoryImpl(CarDataBase.getInstance(context!!)!!))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabs()
        setupToolbar()
    }

    private fun initTabs() {
        tab_layout_tabs.setupWithViewPager(viewpager_tabs)
        tabsAdapter = TabsAdapter(childFragmentManager)
        viewpager_tabs.adapter = tabsAdapter
        with(tabsAdapter) {
            addFragment(BasicView(), string(R.string.tabs_first))
            addFragment(TechView(), string(R.string.tabs_second))
        }
    }

    private fun setupToolbar() = with(activity as MainActivity) {
        setSupportActionBar(toolbar_tabs)
        supportActionBar?.title = null
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_tabs, menu)
        menu?.let {
            internetStatusMenuItem = it.findItem(R.id.action_wifi)
            searchMenuItem = it.findItem(R.id.action_search)
            compareMenuItem = it.findItem(R.id.action_compare)
        }

        searchView = (searchMenuItem.actionView as SearchView).apply {
            setOnQueryTextListener(this@TabsView)
            setOnCloseListener { closeCompareSearch() }
            queryHint = string(R.string.tabs_search_hint)
        }

        when (isComparing) {
            true -> compareMenuItem.setIcon(R.drawable.ic_compare_selected)
            false -> compareMenuItem.setIcon(R.drawable.ic_compare_unselected)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.action_save -> onActionSave()
        R.id.action_compare -> onActionCompare()
        else -> super.onOptionsItemSelected(item)
    }

    private fun onActionCompare(): Boolean {
        isComparing = !isComparing
        if (isComparing) {
            compareMenuItem.setIcon(R.drawable.ic_compare_selected)
            searchMenuItem.expandActionView()
            showBottomCompareText()

        } else {
            compareMenuItem.setIcon(R.drawable.ic_compare_unselected)
            hideBottomCompareText()
            searchMenuItem.collapseActionView()
        }
        return true
    }

    override fun onQueryTextSubmit(reg: String?) = when (reg?.length) {
        in 2..7 -> {
            val connected = CommonUtils().isConnected(context)
            if (connected) {
                presenter.search(reg?.trim())
                searchMenuItem.collapseActionView()
            }
            true
        }
        else -> {
            showText(resources.getString(R.string.error_reg_limit))
            false
        }
    }

    override fun updateResult(result: Result) {
        EventBus.getDefault().post(result)
    }

    private fun onActionSave(): Boolean {
        val result = (activity as MainActivity).resultCar1
        presenter.onImgSaveClicked(result)
        return true
    }

    override fun navigateToCompareView(result: Result) {
        val homeActivity = (activity as MainActivity)
        closeCompareSearch()

        val gson = GsonBuilder().create()
        val jsonCarOne = gson.toJson(homeActivity.resultCar1)
        val jsonCarTwo = gson.toJson(result)

        val action = TabsViewDirections.actionTabsFragmentToMenuFragment(jsonCarOne, jsonCarTwo)

        // navigate to CompareMenuView
        Navigation.findNavController(view!!).navigate(action)
    }


    override fun closeCompareSearch(): Boolean {
        if (isComparing) {
            isComparing = false
            compareMenuItem.setIcon(R.drawable.ic_compare_unselected)
            hideBottomCompareText()
        }
        return false
    }


    fun string(id: Int): String {
        return context.resources?.getString(id) ?: "N/A"
    }

    override fun onResume() {
        initBroadcast()
        super.onResume()
    }

    private fun initBroadcast() {
        receiver = ConnectivityHandler(this)
        activity!!.registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).unregisterReceiver(receiver)
    }

    override fun isComparing(): Boolean = isComparing

    override fun setProgressVisible() {
        progressbar_tabs.visibility = View.VISIBLE
    }

    override fun setProgressInvisible() {
        progressbar_tabs.visibility = View.INVISIBLE
    }

    private fun showBottomCompareText() {
        tv_tabs_compare.visibility = View.VISIBLE
    }

    private fun hideBottomCompareText() {
        tv_tabs_compare.visibility = View.GONE
    }

    override fun internetOff() {
        showText(R.string.error_no_internet)
        internetStatusMenuItem.isVisible = true
        searchMenuItem.isVisible = false
        compareMenuItem.isVisible = false
    }

    override fun internetOn() {
        internetStatusMenuItem.isVisible = false
        searchMenuItem.isVisible = true
        compareMenuItem.isVisible = true
    }

    override fun showResponseCode(code: Int) = showText("Response code: " + code.toString())

    override fun showMsgCarSaved() = showText(R.string.tabs_car_saved)

    override fun showMsgCarAlreadySaved() = showText(R.string.tabs_car_already_saved)

    override fun onQueryTextChange(newText: String?): Boolean = false

    override fun onDestroy() {
        super.onDestroy()
        presenter.cancelJob()
    }
}
