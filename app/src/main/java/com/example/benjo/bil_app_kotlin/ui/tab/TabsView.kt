package com.example.benjo.bil_app_kotlin.ui.tab


import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.ImageView
import androidx.navigation.Navigation

import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.data.repository.CarRepositoryImpl
import com.example.benjo.bil_app_kotlin.data.room.CarDataBase
import com.example.benjo.bil_app_kotlin.domain.Result
import com.example.benjo.bil_app_kotlin.MainActivity

import com.example.benjo.bil_app_kotlin.ui.basic.BasicView
import com.example.benjo.bil_app_kotlin.ui.history.HistoryFragment
import com.example.benjo.bil_app_kotlin.ui.tech.TechView
import com.example.benjo.bil_app_kotlin.utils.CommonUtils
import com.example.benjo.bil_app_kotlin.utils.ConnectivityHandler
import com.example.benjo.bil_app_kotlin.utils.Constants
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_tabs.*
import org.greenrobot.eventbus.EventBus


class TabsView : BaseFragment(), SearchView.OnQueryTextListener, TabsContract.ViewTabs {
    private val TAG = "TabsView"

    private lateinit var tabsAdapter: TabsAdapter
    override lateinit var presenter: TabsContract.TabsPresenter
    private lateinit var receiver: ConnectivityHandler

    companion object {
        var startComparing = false
    }

    override fun layoutId(): Int = R.layout.fragment_tabs

    override fun onQueryTextSubmit(reg: String?) = when (reg?.length) {
        in 2..7 -> {
            searchview_tabs.onActionViewCollapsed()
            val connected = CommonUtils().isConnected(context)
            if (connected) {
                // problemet jag hade innan var att jag anropade
                // homeActivity.saveResultCar1(presenter.search(reg?.trim()))
                // vilket resultera i att search funktionen returnerade result
                // som var null eftersom launch inte väntade på resultatet.
                // Detta fixade jag genom att i search funktionen vänta på resultatet
                // och sen anropa (activity as MainActivity).saveResultCar1(result)
                // via view.updateResult(result: Result)
                presenter.search(reg?.trim())
            }
            true
        }
        else -> {
            showText(resources.getString(R.string.error_reg_limit))
            false
        }
    }

    override fun setProgressVisible() {
        progressbar_tabs.visibility = View.VISIBLE
    }

    override fun setProgressInvisible() {
        progressbar_tabs.visibility = View.INVISIBLE
    }

    override fun showCompareView(result: Result) {
        val homeActivity = activity as MainActivity
        onCloseSearchCompare()
        // convert the cars result to json and send it to CompareMenuView to compare
        val gson = GsonBuilder().create()
        val action = TabsViewDirections.actionTabsFragmentToMenuFragment(
                gson.toJson(homeActivity.resultCar1),
                gson.toJson(result))

        // navigate to CompareMenuView
        Navigation.findNavController(view!!).navigate(action)
    }

    private fun onImgSaveClick() {
        val result = (activity as MainActivity).resultCar1
        presenter.onImgSaveClicked(result)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = TabsPresenter(this, CarRepositoryImpl(CarDataBase.getInstance(context)!!))
        initTabs()
        initListeners()
        img_tabs_compare.isSelected = startComparing
    }

    private fun initBroadcast() {
        receiver = ConnectivityHandler(this)
        activity!!.registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun initTabs() {
        tablayout_tabs.setupWithViewPager(viewpager_tabs)
        tabsAdapter = TabsAdapter(childFragmentManager)
        viewpager_tabs.adapter = tabsAdapter
        with(tabsAdapter) {
            addFragment(BasicView(), Constants.TITLE_TAB_1)
            addFragment(TechView(), Constants.TITLE_TAB_2)
            addFragment(HistoryFragment(), Constants.TITLE_TAB_3)
        }
    }

    private fun initListeners() {
        img_tabs_save.setOnClickListener { onImgSaveClick() }
        searchview_tabs.setOnQueryTextListener(this)
        searchview_tabs.setOnCloseListener { onCloseSearchCompare() }
        img_tabs_compare.setOnClickListener {
            with(img_tabs_compare) {
                isSelected = !isSelected
                startComparing = isSelected
            }
            onComparing()
        }
    }

    override fun updateResult(result: Result) {
        EventBus.getDefault().post(result)
    }

    override fun onCloseSearchCompare(): Boolean {
        if (startComparing) {
            img_tabs_compare.isSelected = false
            startComparing = false
            hideBottomCompareText()
        }
        return false
    }

    private fun onComparing() {
        if (startComparing) {
            showBottomCompareText()
            searchview_tabs.isIconified = false
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
        img_tabs_wifi_off.visibility = ImageView.VISIBLE
    }

    override fun internetOn() {
        img_tabs_wifi_off.visibility = ImageView.INVISIBLE
    }

    override fun showResponseCode(code: Int) = showText("Response code: " + code.toString())

    override fun showMsgCarSaved() = showText(R.string.view_tabs_car_saved)

    override fun showMsgCarAlreadySaved() = showText(R.string.view_tabs_car_not_saved)

    override fun isComparing(): Boolean = startComparing

    override fun onResume() {
        initBroadcast()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).unregisterReceiver(receiver)
    }

    override fun onQueryTextChange(newText: String?): Boolean = false


}
