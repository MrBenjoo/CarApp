package com.example.benjo.bil_app_kotlin.ui.settings


import android.content.Context
import android.os.Bundle
import com.example.benjo.bil_app_kotlin.App
import kotlinx.android.synthetic.main.fragment_settings.*
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import android.content.Intent

import android.view.View
import com.example.benjo.bil_app_kotlin.MainActivity


class SettingsFragment : BaseFragment(), SettingsContract.View {
    val TAG = "SettingsFragment"
    val days = arrayOf("1", "2", "3", "4", "5", "6", "7")
    var nbrOfDays = 0
    override lateinit var presenter: SettingsContract.Presenter

    override fun layoutId(): Int = R.layout.fragment_settings

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = SettingsPresenter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        settings_btn_change_lang.setOnClickListener { onChangeLanguageClick() }
    }

    override fun showTextCacheMaxAge() {
        showText(nbrOfDays.toString())
    }

    override fun showTextErrorCacheMaxAge() {
        showText("Error.")
    }

    private fun onChangeLanguageClick() {
        val currentLanguage = App.localeManager.getLanguage()
        with(LocaleManager) {
            when (currentLanguage) {
                LANGUAGE_SWEDISH -> setNewLocale(LANGUAGE_ENGLISH)
                LANGUAGE_ENGLISH -> setNewLocale(LANGUAGE_SWEDISH)
            }
        }
    }

    private fun setNewLocale(language: String) {
        App.localeManager.setNewLocale(context, language)

        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        )
        System.exit(0)
    }

}

