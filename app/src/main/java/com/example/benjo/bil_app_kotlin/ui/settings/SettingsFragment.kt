package com.example.benjo.bil_app_kotlin.ui.settings


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.domain.Result
import com.example.benjo.bil_app_kotlin.ui.home.HomeContract

class SettingsFragment : Fragment(), HomeContract.View {

    override fun showResponseCode(code: Int) {

    }

    override fun showText(text: String?) {

    }

    override fun saveJsonAndOpenTabs(result: Result?) {

    }


    override lateinit var presenter: HomeContract.Presenter


    override fun getContext(): Context = activity!!.applicationContext


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun showText(textID: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
