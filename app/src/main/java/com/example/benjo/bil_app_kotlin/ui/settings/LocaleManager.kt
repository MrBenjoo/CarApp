package com.example.benjo.bil_app_kotlin.ui.settings

import android.content.Context
import android.annotation.SuppressLint
import android.preference.PreferenceManager
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*


class LocaleManager(context : Context) {
    companion object {
        val LANGUAGE_ENGLISH = "en"
        val LANGUAGE_SWEDISH = "sv"
    }

    private val LANGUAGE_KEY = "language_key"

    private var prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun setLocale(c: Context): Context {
        return updateResources(c, getLanguage())
    }

    fun setNewLocale(c: Context, language: String): Context {
        persistLanguage(language)
        return updateResources(c, language)
    }

    fun getLanguage(): String? {
        return prefs.getString(LANGUAGE_KEY, LANGUAGE_SWEDISH)
    }

    @SuppressLint("ApplySharedPref")
    private fun persistLanguage(language: String) {
        // use commit() instead of apply(), because sometimes we kill the application process immediately
        // which will prevent apply() to finish
        prefs.edit().putString(LANGUAGE_KEY, language).commit()
    }

    private fun updateResources(context: Context, language: String?): Context {
        var context = context
        val locale = Locale(language)
        Locale.setDefault(locale)

        val res = context.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        context = context.createConfigurationContext(config)

        return context
    }

}