package com.example.benjo.bil_app_kotlin.ui.tab


import com.example.benjo.bil_app_kotlin.base.BaseView
import com.example.benjo.bil_app_kotlin.data.network.model.SearchResponse
import com.example.benjo.bil_app_kotlin.ui.compare.data.model.Compare

interface TabsContract {

    interface ViewTabs : BaseView<TabsPresenter> {
        fun updateResult(searchResponse: SearchResponse)

        fun isComparing(): Boolean
        fun closeCompareSearch(): Boolean
        fun setProgressVisible()
        fun setProgressInvisible()
        fun stateInternetOn()
        fun stateInternetOff()
        fun showExceptionError(exception: Exception)
        fun showClientError()
        fun showServerError()
        fun showTextCarSaved()
        fun showTextCarAlreadySaved()
        fun getResponseCarOne(): SearchResponse?

        fun navigateToCompareView(action: TabsViewDirections.ActionTabsFragmentToMenuFragment)
        fun navigateToCompareView(compare: Compare)

        fun compareModeSelected()
        fun compareModeUnselected()
    }

    interface TabsPresenter {
        fun search(reg: String?)
        fun onActionSaveFake(searchResponse: SearchResponse?) : Boolean
        fun cancelJob()
        fun onActionCompare(): Boolean
        fun onActionSave(): Boolean
    }

}