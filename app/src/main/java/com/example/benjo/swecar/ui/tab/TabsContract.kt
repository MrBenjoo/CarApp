package com.example.benjo.swecar.ui.tab


import com.example.benjo.swecar.base.BaseView

interface TabsContract {

    interface View : BaseView<Presenter> {
        fun showCompareMode()
        fun hideCompareMode()
        fun navigateToCompareView()
        fun showInternetOff()
        fun showInternetOn()
        fun isComparing(): Boolean
        fun showProgress()
        fun hideProgress()
        fun showExceptionError(exception: Exception)
        fun showClientError()
        fun showServerError()
        fun showTextCarSaved()
        fun showTextCarAlreadySaved()
    }

    interface Presenter {
        fun onEvent(event: TabsEvent<String>)
        fun onActionSaveFake(): Boolean
    }

}

data class Row(var desc: String?, var data: String?)

sealed class TabsEvent<out T> {
    data class OnSearch<out String>(val reg: String) : TabsEvent<String>()
    object OnActionSave : TabsEvent<Nothing>()
    object OnActionCompare : TabsEvent<Nothing>()
    object OnDestroy : TabsEvent<Nothing>()
}