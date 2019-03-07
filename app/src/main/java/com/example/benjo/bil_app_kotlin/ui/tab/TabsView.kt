package com.example.benjo.bil_app_kotlin.ui.tab


import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.Navigation

import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.data.db.repository.CarRepositoryImpl
import com.example.benjo.bil_app_kotlin.data.db.CarDataBase
import com.example.benjo.bil_app_kotlin.data.network.model.SearchResponse

import com.example.benjo.bil_app_kotlin.ui.basic.BasicView
import com.example.benjo.bil_app_kotlin.ui.tech.TechView
import kotlinx.android.synthetic.main.fragment_tabs.*
import org.greenrobot.eventbus.EventBus
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.di.CarServiceLocator.provideCarService
import com.example.benjo.bil_app_kotlin.ui.compare.CompareViewModel
import com.example.benjo.bil_app_kotlin.ui.compare.data.model.Compare
import com.example.benjo.bil_app_kotlin.utils.*


class TabsView : BaseFragment(), SearchView.OnQueryTextListener, TabsContract.ViewTabs, Toolbar.OnMenuItemClickListener {
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
        presenter = TabsPresenter(this,
                CarRepositoryImpl(CarDataBase.getInstance(context!!)!!),
                provideCarService(),
                ViewModelProviders.of(activity!!).get(CompareViewModel::class.java))
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.action_save -> {
            //presenter.onActionSave()
            presenter.onActionSaveFake(getResponseCarOne())
        }
        R.id.action_compare -> presenter.onActionCompare()
        else -> false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayout()
        setupToolbar(toolbar_tabs, this)
    }

    private fun setupTabLayout() {
        tab_layout_tabs.setupWithViewPager(viewpager_tabs)
        tabsAdapter = TabsAdapter(childFragmentManager)
        viewpager_tabs.adapter = tabsAdapter
        with(tabsAdapter) {
            addFragment(BasicView(), string(R.string.tabs_first))
            addFragment(TechView(), string(R.string.tabs_second))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_tabs, menu)
        initMenuItems(menu)
        setupSearchView(searchMenuItem.actionView as SearchView)
        when (isComparing) {
            true -> compareMenuItem.setIcon(R.drawable.ic_compare_selected)
            false -> compareMenuItem.setIcon(R.drawable.ic_compare_unselected)
        }
    }

    private fun setupSearchView(searchView: SearchView) {
        this.searchView = searchView.apply {
            setOnQueryTextListener(this@TabsView)
            setOnCloseListener { closeCompareSearch() }
            queryHint = string(R.string.tabs_search_hint)
        }
    }

    private fun initMenuItems(menu: Menu?) {
        menu?.let {
            internetStatusMenuItem = it.findItem(R.id.action_wifi)
            searchMenuItem = it.findItem(R.id.action_search)
            compareMenuItem = it.findItem(R.id.action_compare)
        }
    }

    override fun compareModeSelected() {
        compareMenuItem.setIcon(R.drawable.ic_compare_selected)
        searchMenuItem.expandActionView()
        showBottomCompareText()
    }

    override fun compareModeUnselected() {
        compareMenuItem.setIcon(R.drawable.ic_compare_unselected)
        hideBottomCompareText()
        searchMenuItem.collapseActionView()
    }

    override fun onQueryTextSubmit(reg: String?) = when (reg?.length) {
        in 2..7 -> {
            presenter.search(reg?.trim())
            searchMenuItem.collapseActionView()
            true
        }
        else -> {
            showText(string(R.string.error_reg_limit))
            false
        }
    }

    override fun closeCompareSearch(): Boolean {
        if (isComparing) {
            isComparing = false
            compareMenuItem.setIcon(R.drawable.ic_compare_unselected)
            hideBottomCompareText()
        }
        return false
    }

    override fun onResume() {
        registerConnectionBroadcast()
        super.onResume()
    }


    private fun registerConnectionBroadcast() {
        receiver = ConnectivityHandler(this)
        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    private fun showBottomCompareText() = showView(tv_tabs_compare)

    private fun hideBottomCompareText() = hideView(tv_tabs_compare)

    override fun getResponseCarOne(): SearchResponse? = mainActivity().searchResponseCar1

    override fun updateResult(searchResponse: SearchResponse) = EventBus.getDefault().post(searchResponse)

    override fun isComparing(): Boolean = isComparing

    override fun setProgressVisible() = showProgessBar(progressbar_tabs)

    override fun setProgressInvisible() = hideProgressBar(progressbar_tabs)

    override fun showExceptionError(exception: Exception) = showText(exception.message)

    override fun showClientError() = showText(R.string.api_error_client)

    override fun showServerError() = showText(R.string.api_error_server)

    override fun showTextCarSaved() = showText(R.string.tabs_car_saved)

    override fun showTextCarAlreadySaved() = showText(R.string.tabs_car_already_saved)

    override fun onQueryTextChange(newText: String?): Boolean = false

    override fun navigateToCompareView(action: TabsViewDirections.ActionTabsFragmentToMenuFragment) {
        Navigation.findNavController(view!!).navigate(action)
    }

    override fun navigateToCompareView(compare: Compare) {
        mainActivity().compare = compare
        navigate(R.id.action_tabsFragment_to_menuFragment)
    }

    override fun stateInternetOff() {
        showText(R.string.error_no_internet)
        internetStatusMenuItem.isVisible = true
        searchMenuItem.isVisible = false
        compareMenuItem.isVisible = false
    }

    override fun stateInternetOn() {
        /*internetStatusMenuItem.isVisible = false
        searchMenuItem.isVisible = true
        compareMenuItem.isVisible = true
        */
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.cancelJob()
    }

}
