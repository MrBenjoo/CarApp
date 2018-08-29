package com.example.benjo.bil_app_kotlin.home


import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.example.benjo.bil_app_kotlin.R
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), SearchView.OnQueryTextListener, HomeContract.View {
    private val TAG = "HomeFragment"
    override lateinit var presenter: HomeContract.Presenter


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?):
            View? = inflater.inflate(R.layout.fragment_home, container, false)


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HomeActivity)
            presenter = context.presenter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_saved.setOnClickListener { Navigation.findNavController(it).navigate(R.id.savedFragment) }
        tv_settings.setOnClickListener { Navigation.findNavController(it).navigate(R.id.settingsFragment) }
        home_search_view.setOnQueryTextListener(this)
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

    fun showText(textId: Int) {
        Snackbar.make(this.view!!, textId, Snackbar.LENGTH_LONG).show()
    }

    override fun getContext(): Context = activity!!.applicationContext

    override fun onQueryTextChange(newText: String?): Boolean = false

}
