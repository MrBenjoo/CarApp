package com.example.benjo.bil_app_kotlin.ui.saved


import com.example.benjo.bil_app_kotlin.base.BasePresenter
import com.example.benjo.bil_app_kotlin.base.BaseView
import com.example.benjo.bil_app_kotlin.data.db.model.CarData

interface SavedContract {

    interface View : BaseView<Presenter> {
        fun isShowingDeleteIcon(): Boolean
        fun startActionMode()
        fun showDialogOnMultipleDeletion()
        fun setAdapter(savedAdapter: SavedAdapter)
        fun showCar(car: CarData)
        fun showNumberOfDeletedCars(nbrOfDeletedCars : Int)
        fun hideToolbarIcons()
        fun showToolbarIcons()
        fun setActionModeTitle(title: String)
        fun finishActionMode()
        fun showDeleteIcon()
        fun hideDeleteIcon()
    }

    interface Presenter : BasePresenter<View> {
        fun loadSavedCars()
        fun onDestroyActionMode()
        fun onDeleteClick()
        fun getListAdapter(): SavedAdapter
        fun selectAllRows()
        fun startActionMode()
        fun onEditClick()
        fun onSelectAllClick()
        fun onEvent(event: SavedListEvent<EventData>)
        fun unregister()
        fun register()
        fun unBind()
    }
}

data class EventData(val row : CarData, val position : Int)

sealed class SavedListEvent<out T> {
    data class OnShortClick<out EventData>(val data: EventData) : SavedListEvent<EventData>()
    data class OnLongClick<out EventData>(val data: EventData) : SavedListEvent<EventData>()
    object OnEditClick : SavedListEvent<Nothing>()
    object OnSelectAllClick : SavedListEvent<Nothing>()
    object OnDeleteClick : SavedListEvent<Nothing>()
}