package com.example.benjo.bil_app_kotlin.ui.tab


import com.example.benjo.bil_app_kotlin.base.BaseView
import com.example.benjo.bil_app_kotlin.data.network.model.Result

interface TabsContract {

    interface ViewTabs : BaseView<TabsPresenter> {
        fun internetOn()
        fun internetOff()
        fun showResponseCode(code: Int)
        fun updateResult(result: Result)
        fun showMsgCarSaved()
        fun showMsgCarAlreadySaved()
        fun isComparing(): Boolean
        fun navigateToCompareView(result: Result)
        fun setProgressVisible()
        fun setProgressInvisible()
        fun closeCompareSearch(): Boolean
    }

    interface TabsPresenter {
        fun search(reg: String?)
        fun onImgSaveClicked(result: Result?)
        fun cancelJob()
    }

}