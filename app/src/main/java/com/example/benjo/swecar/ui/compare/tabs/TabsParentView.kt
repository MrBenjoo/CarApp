package com.example.benjo.swecar.ui.compare.tabs


import android.os.Bundle
import android.view.View
import com.example.benjo.swecar.R
import com.example.benjo.swecar.base.BaseFragment
import com.example.benjo.swecar.ui.compare.CompareAdapter
import com.example.benjo.swecar.ui.compare.CompareMenuView
import com.example.benjo.swecar.utils.mainActivity
import com.example.benjo.swecar.utils.string

import kotlinx.android.synthetic.main.fragment_compare_parent.*



class TabsParentView : BaseFragment() {
    private val TAG = "TabsParentView"

    override fun layoutId(): Int = R.layout.fragment_compare_parent

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set toolbar navigation click listener
        toolbar_compare_parent.setNavigationOnClickListener { activity?.onBackPressed() }

        // init adapter
        val adapterTabsPage = CompareAdapter(childFragmentManager)
        viewpager_compare_parent.adapter = adapterTabsPage

        // Add fragments to map
        when (mainActivity().selected) {
            CompareMenuView.FORDONSDATA -> {
                adapterTabsPage.addFragment(VehicleView(), string(R.string.compare_tab_vehicle_data))
            }
            CompareMenuView.TEKNISK -> {
                adapterTabsPage.addFragment(EnvironmentView(), string(R.string.compare_tab_environment))
                adapterTabsPage.addFragment(MotorView(), string(R.string.compare_tab_motor))
                adapterTabsPage.addFragment(OtherTechView(), string(R.string.compare_tab_other))
            }
            CompareMenuView.DIMENSIONER -> {
                adapterTabsPage.addFragment(WeightsView(), string(R.string.compare_tab_weights))
                adapterTabsPage.addFragment(WheelsView(), string(R.string.compare_tab_wheels))
                adapterTabsPage.addFragment(OtherDimensionsView(), string(R.string.compare_tab_other))
            }
        }

        // Setup tab layout with viewpager
        smart_tab_layout_compare_parent.setViewPager(viewpager_compare_parent)
    }

}
