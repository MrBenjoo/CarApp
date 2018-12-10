package com.example.benjo.bil_app_kotlin.ui.comparing.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.ui.comparing.CompareAdapter
import com.example.benjo.bil_app_kotlin.ui.comparing.CompareMenuView
import com.example.benjo.bil_app_kotlin.MainActivity

import kotlinx.android.synthetic.main.fragment_compare_parent.*


class ParentView : BaseFragment() {
    private var mapFragments = HashMap<String, Fragment>()


    override fun layoutId(): Int = R.layout.fragment_compare_parent


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar_compare_parent.setNavigationOnClickListener { activity?.onBackPressed() }

        val adapterTabsPage = CompareAdapter(childFragmentManager)
        viewpager_compare_parent.adapter = adapterTabsPage

        // Add fragments to map
        prepareFragmentsMap()

        // Add fragments to adapter from map
        for ((key, value) in mapFragments) adapterTabsPage.addFragment(value, key)

        // Set tablayout with viewpager
        smart_tab_layout_compare_parent.setViewPager(viewpager_compare_parent)

        Log.d("ParentView", (activity as MainActivity).compare.toString())

    }

    private fun prepareFragmentsMap() = when ((activity as MainActivity).selected) {
        CompareMenuView.TEKNISK -> {
            mapFragments["Motor"] = MotorView()
            mapFragments["Miljö"] = EnvironmentView()
            mapFragments["Övrigt"] = OtherTechView()
        }
        CompareMenuView.DIMENSIONER -> {
            mapFragments["Vikter"] = WeightsView()
            mapFragments["Hjul"] = WheelsView()
            mapFragments["Övrigt"] = OtherDimensionsView()
        }
        CompareMenuView.FORDONSDATA -> {
            mapFragments["Fordonsdata"] = VehicleView()
        }
        else -> Unit
    }

}
