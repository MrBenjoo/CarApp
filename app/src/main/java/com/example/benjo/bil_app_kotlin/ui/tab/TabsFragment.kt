package com.example.benjo.bil_app_kotlin.ui.tab


import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation

import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.adapters.AdapterTabsPage
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.data.model.Result
import com.example.benjo.bil_app_kotlin.data.repository.CarRepositoryImpl
import com.example.benjo.bil_app_kotlin.data.room.CarData
import com.example.benjo.bil_app_kotlin.data.room.CarDataBase
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity
import com.example.benjo.bil_app_kotlin.ui.basic.BasicFragment
import com.example.benjo.bil_app_kotlin.ui.tech.TechFragment
import com.example.benjo.bil_app_kotlin.utils.CommonUtils
import com.example.benjo.bil_app_kotlin.utils.ConnectivityHandler
import com.example.benjo.bil_app_kotlin.utils.Constants
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_tabs.*


class TabsFragment : BaseFragment(), SearchView.OnQueryTextListener, TabsContract.ViewTabs {

    override fun layoutId(): Int = R.layout.fragment_tabs

    private lateinit var adapterTabsPage: AdapterTabsPage
    override lateinit var presenter: TabsContract.TabsPresenter
    private lateinit var receiver: ConnectivityHandler

    companion object {
        var isComparing = false
    }

    override fun onQueryTextSubmit(query: String?) = when (query?.length) {
        in 2..7 -> {
            search_view.onActionViewCollapsed()
            val homeActivity = activity as HomeActivity
            if (isComparing) {

                /* Read a fake JSON to simulate the real situation. In the real situation the JSON
                will be retrieved from the presenter  */
                homeActivity.saveSecondJson(CommonUtils().loadJSONFromAsset(context, "bil_2.json"))
                /* ---------------------------------------------------------------------------------------------------------- */

                onCloseSearchCompare()
                val action = with(homeActivity) {
                    TabsFragmentDirections.actionTabsFragmentToMenuFragment(firstJson!!, secondJson!!)
                }
                Navigation.findNavController(view!!).navigate(action)

            } else homeActivity.saveFirstJson(query)

            /*response = presenter.search(query?.trim())
            val validatedResponse = presenter.validateResponse(response)
            if (validatedResponse != null) {
                basicPresenter.updateTab(validatedResponse)
                techPresenter.updateTab(validatedResponse)
            }*/

            true
        }
        else -> {
            showText(resources.getString(R.string.error_reg_limit))
            false
        }
    }

    private fun onImgSaveClick() {
        val result = GsonBuilder().create().fromJson((activity as HomeActivity).firstJson, Result::class.java)
        with(result?.carInfo!!) {
            val reg = attributes?.regno!!
            val model = basic?.data?.model!!
            val modelYear = basic.data.model_year!!
            val type = basic.data.type!!
            val vin = attributes.vin!!
            val json = GsonBuilder().create().toJson(result)
            val saved = presenter.saveToDatabase(CarData(null, reg, model, modelYear, type, vin, json))
            if (saved) showText(R.string.view_tabs_car_saved)
            else showText(R.string.view_tabs_car_not_saved)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = TabsPresenter(this, CarRepositoryImpl(CarDataBase.getInstance(context)!!))
        initBroadcast()
        initTabs()
        initListeners()
        iv_toolbar_compare.isSelected = isComparing
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
            addFragment(BasicFragment(), Constants.TITLE_TAB_1)
            addFragment(TechFragment(), Constants.TITLE_TAB_2)
            addFragment(BasicFragment(), Constants.TITLE_TAB_3)
        }
    }

    private fun initListeners() {
        img_save.setOnClickListener { onImgSaveClick() }
        search_view.setOnQueryTextListener(this)
        search_view.setOnCloseListener { onCloseSearchCompare() }
        iv_toolbar_compare.setOnClickListener {
            with(iv_toolbar_compare) {
                isSelected = !isSelected
                isComparing = isSelected
            }
            onComparing()
        }
    }

    private fun onCloseSearchCompare(): Boolean {
        if (isComparing) {
            iv_toolbar_compare.isSelected = false
            isComparing = false
            hideBottomCompareText()
        }
        return false
    }

    private fun onComparing() {
        if (isComparing) {
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

    override fun onDestroy() {
        super.onDestroy()
        (activity as HomeActivity).unregisterReceiver(receiver)
    }

    override fun onQueryTextChange(newText: String?): Boolean = false


}
