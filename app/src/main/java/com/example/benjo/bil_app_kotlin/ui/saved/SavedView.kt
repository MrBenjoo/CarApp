package com.example.benjo.bil_app_kotlin.ui.saved


import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.ActionMode

import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.MainActivity
import com.example.benjo.bil_app_kotlin.data.room.CarData
import kotlinx.android.synthetic.main.dialog_delete_car.view.*
import android.view.*
import androidx.navigation.Navigation
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.data.repository.CarRepositoryImpl
import com.example.benjo.bil_app_kotlin.data.room.CarDataBase
import com.example.benjo.bil_app_kotlin.domain.Result
import com.example.benjo.bil_app_kotlin.utils.builder.FragmentToolbar
import com.example.benjo.bil_app_kotlin.utils.builder.ToolbarManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_saved.*


class SavedView : BaseFragment(), SavedContract.View, ActionMode.Callback {
    private val TAG = "SavedView"
    override lateinit var presenter: SavedContract.Presenter
    override fun layoutId(): Int = R.layout.fragment_saved

    override fun startActionMode(): android.view.ActionMode? {
        return activity!!.startActionMode(this)
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean = when (item!!.itemId) {
        R.id.action_delete_saved_view -> {
            showDialogOnMultipleDeletion()
            true
        }
        R.id.action_delete_all_saved_view -> {
            presenter.onEvent(SavedListEvent.OnDeleteAllClickFromActionMode)
            true
        }
        else -> false
    }

    override fun showEmptyListMessage() {
        showText(R.string.saved_list_empty)
    }

    override fun showNumberOfDeletedCars(nbrOfDeletedCars: Int) {
        val text = resources.getString(R.string.saved_list_nbr_deleted_cars, nbrOfDeletedCars)
        showText(text)
    }

    override fun showDialogOnMultipleDeletion() {
        val deleteDialogView = getDialogView()
        val dialog = AlertDialog.Builder(activity!!).create()
        dialog.setView(deleteDialogView)
        deleteDialogView?.tv_saved_dialog_yes?.setOnClickListener {
            presenter.onEvent(SavedListEvent.OnDeleteClickFromActionMode)
            dialog.cancel()
        }
        deleteDialogView?.tv_saved_dialog_cancel?.setOnClickListener { dialog.cancel() }
        dialog.show()
    }


    override fun setAdapter(savedAdapter: SavedAdapter) = with(recyclerview_saved) {
        setHasFixedSize(true)
        adapter = savedAdapter
    }

    override fun showCar(car: CarData) {
        showText("showCar: " + car.vin + " (TODO)")
        (activity as MainActivity).resultCar1 = GsonBuilder().create().fromJson(car.json, Result::class.java)
        Navigation.findNavController(this.view!!).navigate(R.id.tabsFragment)
    }

    override fun onDestroyActionMode(p0: ActionMode?) {
        presenter.onDestroyActionMode()
    }

    override fun onCreateActionMode(mode: android.view.ActionMode, menu: Menu): Boolean {
        mode.menuInflater.inflate(R.menu.menu_saved_view, menu)
        return true
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = SavedPresenter(
                CarRepositoryImpl(CarDataBase.getInstance(context!!)!!), SavedAdapter())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        setAdapter(presenter.getListAdapter())
        val toolbar = ToolbarManager(builder(), view).prepareToolbar()
        initToolbarListeners(toolbar)
    }

    private fun initToolbarListeners(toolbar: Toolbar?) = toolbar?.let {
        it.menu.findItem(R.id.action_delete_all_saved_view).setOnMenuItemClickListener {
            presenter.onEvent(SavedListEvent.OnDeleteAllClickFromView)
            true
        }
        it.menu.findItem(R.id.action_delete_saved_view).setOnMenuItemClickListener {
            presenter.onEvent(SavedListEvent.OnDeleteClickFromView)
            true
        }
    }

    private fun builder(): FragmentToolbar = FragmentToolbar.Builder()
            .withId(R.id.toolbar_saved)
            .withNavBackListener({ activity!!.onBackPressed() }, R.id.img_saved_nav_back)
            .withMenu(R.menu.menu_saved_view)
            .build()

    override fun onResume() {
        super.onResume()
        presenter.loadSavedCars()
    }

    private fun getDialogView(): View? = LayoutInflater
            .from(activity)
            .inflate(R.layout.dialog_delete_car, null)

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

}
