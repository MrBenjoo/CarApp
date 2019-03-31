package com.example.benjo.swecar.ui.home


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.benjo.swecar.App
import com.example.benjo.swecar.MainActivity
import com.example.benjo.swecar.R
import com.example.benjo.swecar.base.BaseFragment
import com.example.benjo.swecar.servicelocator.CarServiceLocator.provideCarService
import com.example.benjo.swecar.MainViewModel
import com.example.benjo.swecar.ui.tab.TabsView
import com.example.benjo.swecar.utils.hideView
import com.example.benjo.swecar.utils.navigate
import com.example.benjo.swecar.utils.showText
import com.example.benjo.swecar.utils.showView
import kotlinx.android.synthetic.main.fragment_home.*


class HomeView : BaseFragment(), SearchView.OnQueryTextListener, HomeContract.View {
    private val TAG = "HomeView"
    override lateinit var presenter: HomeContract.Presenter

    override fun layoutId(): Int = R.layout.fragment_home

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = HomePresenter(provideCarService(), ViewModelProviders.of(activity!!).get(MainViewModel::class.java))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        tv_saved_home.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_savedFragment2))
        tv_language_home.setOnClickListener { onChangeLanguageClick() }
        search_view_home.setOnQueryTextListener(this)
    }

    private fun onChangeLanguageClick() {
        val currentLanguage = App.localeManager.getLanguage()
        when (currentLanguage) {
            LocaleManager.LANGUAGE_SWEDISH -> setNewLocale(LocaleManager.LANGUAGE_ENGLISH)
            LocaleManager.LANGUAGE_ENGLISH -> setNewLocale(LocaleManager.LANGUAGE_SWEDISH)
        }
    }

    private fun setNewLocale(language: String) {
        App.localeManager.setNewLocale(context, language)

        val intent = Intent(context, MainActivity::class.java)
        startActivity(
                intent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        )
        System.exit(0)
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

    override fun showExceptionError(error: Exception) = showText(error.message ?: "Exception")

    override fun showClientError() = showText(R.string.api_error_client)

    override fun showServerError() = showText(R.string.api_error_server)

    override fun showProgress() = showView(progressbar_home)

    override fun hideProgress() = hideView(progressbar_home)

    override fun navigateToTabs() = navigate(R.id.action_homeFragment_to_tabsFragment)

    override fun onQueryTextChange(newText: String?): Boolean = false

    override fun onDestroy() {
        super.onDestroy()
        presenter.cancelJob()
    }

}
