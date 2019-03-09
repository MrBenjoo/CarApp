package com.example.benjo.bil_app_kotlin.ui.home


import android.content.Context
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.View
import androidx.navigation.Navigation
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.di.CarServiceLocator.provideCarService
import com.example.benjo.bil_app_kotlin.ui.tab.TabsView
import com.example.benjo.bil_app_kotlin.utils.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeView : BaseFragment(), SearchView.OnQueryTextListener, HomeContract.View {
    private val TAG = "HomeView"
    override lateinit var presenter: HomeContract.Presenter

    override fun layoutId(): Int = R.layout.fragment_home

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = HomePresenter(provideCarService())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        tv_saved_home.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_savedFragment2))
        tv_settings_home.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_settingsFragment))
        search_view_home.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean = when (query?.length) {
        in 2..7 -> {
            search_view_home.onActionViewCollapsed()
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
        when (TabsView.isComparing) {
            true -> showView(tv_compare_home)
            false -> hideView(tv_compare_home)
        }
    }

    override fun showExceptionError(error: Exception) = showText(error.message)

    override fun showClientError() = showText(R.string.api_error_client)

    override fun showServerError() = showText(R.string.api_error_server)

    override fun showProgress() = showProgessBar(progressbar_home)

    override fun hideProgress() = hideProgressBar(progressbar_home)

    override fun navigateToTabs() = navigate(R.id.action_homeFragment_to_tabsFragment)

    override fun onQueryTextChange(newText: String?): Boolean = false

    override fun onDestroy() {
        super.onDestroy()
        presenter.cancelJob()
    }

}
