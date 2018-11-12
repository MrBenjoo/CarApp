package com.example.benjo.bil_app_kotlin.ui.saved


import android.view.ActionMode
import com.example.benjo.bil_app_kotlin.base.BasePresenter
import com.example.benjo.bil_app_kotlin.base.BaseView
import com.example.benjo.bil_app_kotlin.data.model.EventData
import com.example.benjo.bil_app_kotlin.data.room.CarData

interface SavedContract {

    interface View : BaseView<Presenter> {
        fun startActionMode(): ActionMode?
        fun showDialogOnMultipleDeletion()
        fun setAdapter(savedAdapter: SavedAdapter)
        fun showCar(car: CarData)
        fun showEmptyListMessage()
        fun showNumberOfDeletedCars(nbrOfDeletedCars : Int)
    }

    interface Presenter : BasePresenter<View> {
        fun loadSavedCars()
        fun onDestroyActionMode()
        fun onDeleteClickFromActionMode()
        fun getListAdapter(): SavedAdapter
        fun checkAll()
        fun startActionMode()
        fun onDeleteAllClickFromView()
        fun onDeleteAllClickFromActionMode()
        fun onEvent(event: SavedListEvent<EventData>)
    }
}

sealed class SavedListEvent<out T> {
    data class OnLongClick<out EventData>(val data: EventData) : SavedListEvent<EventData>()
    data class OnShortClick<out EventData>(val data: EventData) : SavedListEvent<EventData>()
    object OnDeleteAllClickFromActionMode : SavedListEvent<Nothing>()
    object OnDeleteClickFromActionMode : SavedListEvent<Nothing>()
    object OnDeleteAllClickFromView : SavedListEvent<Nothing>()
    object OnDeleteClickFromView : SavedListEvent<Nothing>()
}