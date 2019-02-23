package com.example.benjo.bil_app_kotlin.ui.settings


import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SettingsPresenter : SettingsContract.Presenter, CoroutineScope {
    private lateinit var view: SettingsContract.View
    private var jobTracker: Job = Job()


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + jobTracker

    override fun changeLanguageTo(language: String?) {

    }

    override fun attachView(view: SettingsContract.View) {
        this.view = view
    }

}