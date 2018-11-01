package com.example.benjo.bil_app_kotlin.ui.home


import android.content.Context
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.View
import androidx.navigation.Navigation

import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.ui.tab.TabsFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment(), SearchView.OnQueryTextListener, HomeContract.View {
    private val TAG = "HomeFragment"
    override lateinit var presenter: HomeContract.Presenter

    override fun layoutId(): Int = R.layout.fragment_home

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HomeActivity) presenter = context.presenter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_saved.setOnClickListener { Navigation.findNavController(it).navigate(R.id.savedFragment) }
        tv_settings.setOnClickListener { Navigation.findNavController(it).navigate(R.id.settingsFragment) }
        home_search_view.setOnQueryTextListener(this)
        presenter.attachView(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean = when (query?.length) {
        in 2..7 -> {
            home_search_view.onActionViewCollapsed()
            presenter.search(query?.trim())
            true
        }
        else -> {
            showText(R.string.error_reg_limit)
            false
        }
    }

    override fun onResume() {
        super.onResume()
        if (TabsFragment.isComparing) tv_compare.visibility = View.VISIBLE
        else tv_compare.visibility = View.GONE
    }

    override fun saveJsonAndOpenTabs(json: String) {
        (activity as HomeActivity).firstJson = json
        Navigation.findNavController(this.view!!).navigate(R.id.tabsFragment)
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

}
