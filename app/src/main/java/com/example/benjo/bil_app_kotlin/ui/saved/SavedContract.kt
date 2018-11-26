package com.example.benjo.bil_app_kotlin.ui.saved


import android.view.ActionMode
import com.example.benjo.bil_app_kotlin.base.BasePresenter
import com.example.benjo.bil_app_kotlin.base.BaseView
import com.example.benjo.bil_app_kotlin.data.room.CarData

interface SavedContract {

    interface View : BaseView<Presenter> {
        fun startActionMode()
        fun showDialogOnMultipleDeletion()
        fun setAdapter(savedAdapter: SavedAdapter)
        fun showCar(car: CarData)
        fun showNumberOfDeletedCars(nbrOfDeletedCars : Int)
        fun hideToolbarIcons()
        fun showToolbarIcons()
        fun setActionModeTitle(title: String)
        fun finishActionMode()
    }

    interface Presenter : BasePresenter<View> {
        fun loadSavedCars()
        fun onDestroyActionMode()
        fun onDeleteClickActionMode()
        fun getListAdapter(): SavedAdapter
        fun selectAllRows()
        fun startActionMode()
        fun onDeleteAllClickView()
        fun onDeleteAllClickActionMode()
        fun onEvent(event: SavedListEvent<EventData>)
        fun unregister()
        fun register()
    }
}

data class EventData(val row : CarData, val position : Int)

sealed class SavedListEvent<out T> {
    data class OnLongClick<out EventData>(val data: EventData) : SavedListEvent<EventData>()
    data class OnShortClick<out EventData>(val data: EventData) : SavedListEvent<EventData>()
    object OnDeleteAllClickActionMode : SavedListEvent<Nothing>()
    object OnDeleteClickActionMode : SavedListEvent<Nothing>()
    object OnDeleteAllClickView : SavedListEvent<Nothing>()
    object OnDeleteClickView : SavedListEvent<Nothing>()
}