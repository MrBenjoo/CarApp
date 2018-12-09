package com.example.benjo.bil_app_kotlin.ui.settings


import android.os.Bundle
import com.example.benjo.bil_app_kotlin.App
import kotlinx.android.synthetic.main.fragment_settings.*
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import android.content.Intent
import android.util.Log
import com.example.benjo.bil_app_kotlin.MainActivity


class SettingsFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_settings

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("LifeCycles", "SettingsFragment -> onActivityCreated")
        settings_btn_change_lang.setOnClickListener { onChangeLanguageClick() }
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

