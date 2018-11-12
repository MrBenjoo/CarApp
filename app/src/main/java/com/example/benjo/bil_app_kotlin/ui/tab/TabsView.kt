package com.example.benjo.bil_app_kotlin.ui.tab


import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.ImageView
import androidx.navigation.Navigation

import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.adapters.AdapterTabsPage
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.data.repository.CarRepositoryImpl
import com.example.benjo.bil_app_kotlin.data.room.CarDataBase
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity
import com.example.benjo.bil_app_kotlin.ui.basic.BasicView
import com.example.benjo.bil_app_kotlin.ui.history.HistoryFragment
import com.example.benjo.bil_app_kotlin.ui.tech.TechView
import com.example.benjo.bil_app_kotlin.utils.ConnectivityHandler
import com.example.benjo.bil_app_kotlin.utils.Constants
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_tabs.*


class TabsView : BaseFragment(), SearchView.OnQueryTextListener, TabsContract.ViewTabs {
    private val TAG = "TabsView"

    private lateinit var adapterTabsPage: AdapterTabsPage
    override lateinit var presenter: TabsContract.TabsPresenter
    private lateinit var receiver: ConnectivityHandler

    companion object {
        var startComparing = false
    }

    override fun layoutId(): Int = R.layout.fragment_tabs

    override fun onQueryTextSubmit(query: String?) = when (query?.length) {
        in 2..7 -> {
            search_view.onActionViewCollapsed()
            val homeActivity = activity as HomeActivity
            if (startComparing) {
                onCloseSearchCompare()

                // read a fake JSON to simulate the real situation
                val gson = GsonBuilder().create()
                homeActivity.saveResultCar2(presenter.search(query?.trim()))

                // convert the cars result to json and send it to MenuFragment to compare
                val action = with(homeActivity) {
                    TabsViewDirections.actionTabsFragmentToMenuFragment(
                            gson.toJson(resultCar1),
                            gson.toJson(resultCar2))
                }

                // navigate to MenuFragment
                Navigation.findNavController(view!!).navigate(action)

            } else {
                homeActivity.saveResultCar1(presenter.search(query?.trim()))
            }
            true
        }
        else -> {
            showText(resources.getString(R.string.error_reg_limit))
            false
        }
    }

    private fun onImgSaveClick() {
        val result = (activity as HomeActivity).resultCar1
        presenter.onImgSaveClicked(result)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = TabsPresenter(this, CarRepositoryImpl(CarDataBase.getInstance(context)!!))
        initTabs()
        initListeners()
        iv_toolbar_compare.isSelected = startComparing
    }

    private fun initBroadcast() {
        receiver = ConnectivityHandler(this)
        activity!!.registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun initTabs() {
        tabs.setupWithViewPager(container_tabs)
        adapterTabsPage = AdapterTabsPage(childFragmentManager)
        container_tabs.adapter = adapterTabsPage
        with(adapterTabsPage) {
            addFragment(BasicView(), Constants.TITLE_TAB_1)
            addFragment(TechView(), Constants.TITLE_TAB_2)
            addFragment(HistoryFragment(), Constants.TITLE_TAB_3)
        }
    }

    private fun initListeners() {
        img_save.setOnClickListener { onImgSaveClick() }
        search_view.setOnQueryTextListener(this)
        search_view.setOnCloseListener { onCloseSearchCompare() }
        iv_toolbar_compare.setOnClickListener {
            with(iv_toolbar_compare) {
                isSelected = !isSelected
                startComparing = isSelected
            }
            onComparing()
        }
    }

    private fun onCloseSearchCompare(): Boolean {
        if (startComparing) {
            iv_toolbar_compare.isSelected = false
            startComparing = false
            hideBottomCompareText()
        }
        return false
    }

    private fun onComparing() {
        if (startComparing) {
            showBottomCompareText()
            search_view.isIconified = false
        } else hideBottomCompareText()
    }

    private fun showBottomCompareText() {
        tv_tabs_compare.visibility = View.VISIBLE
    }

    private fun hideBottomCompareText() {
        tv_tabs_compare.visibility = View.GONE
    }

    override fun internetOff() {
        showText(R.string.error_no_internet)
        info_img_wifi_off.visibility = ImageView.VISIBLE
    }

    override fun internetOn() {
        info_img_wifi_off.visibility = ImageView.INVISIBLE
    }

    override fun onResume() {
        initBroadcast()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        (activity as HomeActivity).unregisterReceiver(receiver)
    }

    override fun onQueryTextChange(newText: String?): Boolean = false


}
