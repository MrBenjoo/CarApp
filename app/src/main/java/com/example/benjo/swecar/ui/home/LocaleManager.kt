package com.example.benjo.swecar.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.preference.PreferenceManager
import java.util.*


class LocaleManager(context : Context) {
    companion object {
        const val LANGUAGE_ENGLISH = "en"
        const val LANGUAGE_SWEDISH = "sv"
    }

    private val LANGUAGE_KEY = "language_key"

    private var prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun setLocale(c: Context): Context = updateResources(c, getLanguage())

    fun setNewLocale(c: Context, language: String): Context {
        persistLanguage(language)
        return updateResources(c, language)
    }

    fun getLanguage(): String? = prefs.getString(LANGUAGE_KEY, LANGUAGE_SWEDISH)


    /**
     * Use commit() instead of apply(), because sometimes we kill the application process immediately
     * which will prevent apply() to finish.
     */
    @SuppressLint("ApplySharedPref")
    private fun persistLanguage(language: String) {
        prefs.edit().putString(LANGUAGE_KEY, language).commit()
    }

    private fun updateResources(context: Context, language: String?): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }

}