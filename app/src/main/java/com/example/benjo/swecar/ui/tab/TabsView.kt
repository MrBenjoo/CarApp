package com.example.benjo.swecar.ui.tab


import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.example.benjo.swecar.App
import com.example.benjo.swecar.R
import com.example.benjo.swecar.base.BaseFragment
import com.example.benjo.swecar.servicelocator.PresenterServiceLocator
import com.example.benjo.swecar.ui.basic.BasicView
import com.example.benjo.swecar.ui.tech.TechView
import com.example.benjo.swecar.utils.*
import kotlinx.android.synthetic.main.fragment_tabs.*


class TabsView : BaseFragment(), SearchView.OnQueryTextListener, TabsContract.View, Toolbar.OnMenuItemClickListener {
    private val TAG = "TabsView"
    private lateinit var receiver: ConnectivityHandler
    private lateinit var internetStatusMenuItem: MenuItem
    private lateinit var searchMenuItem: MenuItem
    private lateinit var compareMenuItem: MenuItem
    private lateinit var searchView: SearchView
    override lateinit var presenter: TabsContract.Presenter


    override fun layoutId(): Int = R.layout.fragment_tabs

    companion object {
        var isComparing = false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = PresenterServiceLocator.provideTabsPresenter(mainActivity(), this)
    }

    override fun onQueryTextSubmit(reg: String?) = when (reg?.length) {
        in 2..7 -> {
            when (App.isConnected()) {
                true -> presenter.onEvent(TabsEvent.OnSearch(reg?.trim().toString()))
                false -> showClientError()
            }
            searchMenuItem.collapseActionView()
            true
        }
        else -> {
            showText(string(R.string.error_reg_limit))
            false
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.action_save -> {
            //presenter.onEvent(TabsEvent.OnActionSave)
            presenter.onActionSaveFake()
        }
        R.id.action_compare -> {
            presenter.onEvent(TabsEvent.OnActionCompare)
            true
        }
        else -> false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayout()
        setupToolbar(toolbar_tabs, this)
    }

    private fun setupTabLayout() {
        tab_layout_tabs.setupWithViewPager(viewpager_tabs)
        val tabsAdapter = TabsAdapter(childFragmentManager)
        viewpager_tabs.adapter = tabsAdapter
        tabsAdapter.addFragment(BasicView(), string(R.string.tabs_first))
        tabsAdapter.addFragment(TechView(), string(R.string.tabs_second))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_tabs, menu)
        initMenuItems(menu)
        setupSearchView(searchMenuItem.actionView as SearchView)
        when (isComparing) {
            true -> compareMenuItem.setIcon(R.drawable.ic_compare_selected)
            false -> compareMenuItem.setIcon(R.drawable.ic_compare_unselected)
        }
    }

    private fun initMenuItems(menu: Menu?) = menu?.let {
        internetStatusMenuItem = it.findItem(R.id.action_wifi)
        searchMenuItem = it.findItem(R.id.action_search)
        compareMenuItem = it.findItem(R.id.action_compare)
    }

    private fun setupSearchView(searchView: SearchView) {
        this.searchView = searchView.apply {
            setOnQueryTextListener(this@TabsView)
            setOnCloseListener { closeCompareModeSearch() }
            queryHint = string(R.string.tabs_search_hint)
        }
    }

    override fun showCompareMode() {
        showView(tv_tabs_bottom_compare_text)
        compareMenuItem.setIcon(R.drawable.ic_compare_selected)
        searchMenuItem.expandActionView()
    }

    override fun hideCompareMode() {
        hideView(tv_tabs_bottom_compare_text)
        compareMenuItem.setIcon(R.drawable.ic_compare_unselected)
        searchMenuItem.collapseActionView()
    }

    private fun closeCompareModeSearch(): Boolean {
        hideView(tv_tabs_bottom_compare_text)
        compareMenuItem.setIcon(R.drawable.ic_compare_unselected)
        isComparing = false
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

    override fun navigateToCompareView() {
        closeCompareModeSearch()
        navigate(R.id.action_tabsFragment_to_menuFragment)
    }

    override fun showInternetOff() {
        showText(R.string.error_no_internet)
        internetStatusMenuItem.isVisible = true
        searchMenuItem.isVisible = false
        compareMenuItem.isVisible = false
    }

    override fun showInternetOn() {
        showText(R.string.connection_restored)
        internetStatusMenuItem.isVisible = false
        searchMenuItem.isVisible = true
        compareMenuItem.isVisible = true
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onEvent(TabsEvent.OnDestroy)
    }

    override fun isComparing(): Boolean = isComparing

    override fun showProgress() = showView(progressbar_tabs)

    override fun hideProgress() = hideView(progressbar_tabs)

    override fun showExceptionError(exception: Exception) = showText(exception.message
            ?: "Exception")

    override fun showClientError() = showText(R.string.api_error_client)

    override fun showServerError() = showText(R.string.api_error_server)

    override fun showTextCarSaved() = showText(R.string.tabs_car_saved)

    override fun showTextCarAlreadySaved() = showText(R.string.tabs_car_already_saved)

    override fun onQueryTextChange(newText: String?): Boolean = false

}
