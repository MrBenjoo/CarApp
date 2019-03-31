package com.example.benjo.swecar.ui.home

import com.example.benjo.swecar.base.BasePresenter
import com.example.benjo.swecar.base.BaseView

interface HomeContract {

    interface View : BaseView<Presenter> {
        fun navigateToTabs()
        fun showProgress()
        fun hideProgress()
        fun showExceptionError(error: Exception)
        fun showClientError()
        fun showServerError()
    }

    interface Presenter : BasePresenter<View> {
        fun search(reg: String?)
        fun cancelJob()
    }

}

