package com.example.benjo.bil_app_kotlin.ui.settings

import com.example.benjo.bil_app_kotlin.base.BasePresenter
import com.example.benjo.bil_app_kotlin.base.BaseView

interface SettingsContract {

    interface Presenter : BasePresenter<View> {
        fun changeLanguageTo(language : String?)
    }

    interface View : BaseView<Presenter> {
        fun showTextCacheMaxAge()
        fun showTextErrorCacheMaxAge()
    }
}