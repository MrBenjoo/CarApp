package com.example.benjo.bil_app_kotlin.ui.comparing


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.example.benjo.bil_app_kotlin.utils.builder.ToolbarManager
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.adapters.ComparingTabsAdapter
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity
import com.example.benjo.bil_app_kotlin.utils.builder.FragmentToolbar
import com.example.comparing.ui.comparing.*

import kotlinx.android.synthetic.main.fragment_parent_compare.*


class ParentCompareFragment : BaseFragment() {
    private var mapFragments = HashMap<String, Fragment>()


    override fun layoutId(): Int = R.layout.fragment_parent_compare


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapterTabsPage = ComparingTabsAdapter(childFragmentManager)
        viewpager_comp_tech.adapter = adapterTabsPage

        // Add fragments to map
        prepareFragmentsMap()

        // Add fragments to adapter from map
        for ((key, value) in mapFragments) adapterTabsPage.addFragment(value, key)

        // Set tablayout with viewpager
        smart_tab_layout_comp_tech.setViewPager(viewpager_comp_tech)

        // Build toolbar
        ToolbarManager(builder(), view).prepareToolbar()
    }

    private fun prepareFragmentsMap() = when ((activity as HomeActivity).selected) {
        MenuFragment.TEKNISK -> {
            mapFragments["Wheels"] = WheelsFragment()
            mapFragments["Dimensions"] = DimensionsFragment()
            mapFragments["Fuel/Emission"] = FuelEmissionFragment()
            mapFragments["Weigth"] = WeightFragment()
            mapFragments["Motor"] = MotorFragment()
        }
        MenuFragment.DIMENSIONER -> Unit
        MenuFragment.ÖVRIGT -> Unit
        else -> Unit
    }


    private fun builder(): FragmentToolbar = FragmentToolbar.Builder()
            .withId(R.id.toolbar_saved)
            .withNavBackListener({ activity!!.onBackPressed() }, R.id.nav_back_saved)
            .build()


}
