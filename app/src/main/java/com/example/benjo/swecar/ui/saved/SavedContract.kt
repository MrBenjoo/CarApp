package com.example.benjo.swecar.ui.saved

import com.example.benjo.swecar.base.BasePresenter
import com.example.benjo.swecar.base.BaseView
import com.example.benjo.swecar.data.db.model.CarData

interface SavedContract {

    interface View : BaseView<Presenter> {
        fun isShowingDeleteIcon(): Boolean
        fun startActionMode()
        fun showDialogOnMultipleDeletion()
        fun setAdapter(savedAdapter: SavedAdapter)
        fun navigateToTabs()
        fun showNumberOfDeletedCars(nbrOfDeletedCars: Int)
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
        fun getListAdapter(): SavedAdapter
        fun startActionMode()
        fun onEvent(event: SavedListEvent<RowEventData>)
        fun unregister()
        fun register()
        fun unBind()
    }
}

data class RowEventData(val row: CarData, val position: Int)

sealed class SavedListEvent<out T> {
    data class OnShortClick<out EventData>(val data: EventData) : SavedListEvent<EventData>()
    data class OnLongClick<out EventData>(val data: EventData) : SavedListEvent<EventData>()
    object OnEditClick : SavedListEvent<Nothing>()
    object OnSelectAllClick : SavedListEvent<Nothing>()
    object OnDeleteClick : SavedListEvent<Nothing>()
    object OnDestroy : SavedListEvent<Nothing>()
}