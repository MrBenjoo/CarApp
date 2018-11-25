package com.example.benjo.bil_app_kotlin.ui.saved


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
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
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_saved.*


class SavedView : BaseFragment(), SavedContract.View, ActionMode.Callback, Toolbar.OnMenuItemClickListener{


    private val TAG = "SavedView"
    override lateinit var presenter: SavedContract.Presenter
    override fun layoutId(): Int = R.layout.fragment_saved
    private var showToolbarIcons = false

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


    override fun setAdapter(savedAdapter: SavedAdapter) {
        with(recyclerview_saved) {
            setHasFixedSize(true)
            adapter = savedAdapter
            addItemDecoration(ItemDivideDecoration(context))
        }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initPresenter()
        setAdapter(presenter.getListAdapter())
    }

    private fun initToolbar() {
        with(activity as MainActivity) {
            setSupportActionBar(toolbar_saved)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = null
        }
        toolbar_saved.setOnMenuItemClickListener(this)
    }

    private fun initPresenter() {
        val room = CarDataBase.getInstance(activity!!.applicationContext)!!
        val savedViewModel = ViewModelProviders.of(activity!!).get(SavedViewModel::class.java)
        presenter = SavedPresenter(CarRepositoryImpl(room), SavedAdapter(), savedViewModel)
        presenter.attachView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_saved_view, menu)

        val deleteAllItem = menu?.findItem(R.id.action_delete_all_saved_view)
        val deleteItem = menu?.findItem(R.id.action_delete_saved_view)

        when (showToolbarIcons) {
            true -> {
                deleteAllItem?.isVisible = true
                deleteItem?.isVisible = true
            }
            false -> {
                deleteAllItem?.isVisible = false
                deleteItem?.isVisible = false
            }
        }

    }


    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_delete_all_saved_view -> {
                presenter.onEvent(SavedListEvent.OnDeleteAllClickFromView)
                return true
            }
            R.id.action_delete_saved_view -> {
                presenter.onEvent(SavedListEvent.OnDeleteClickFromView)
                return true
            }
        }
        return false
    }

    override fun hideToolbarIcons() {
        showToolbarIcons = false
        activity!!.invalidateOptionsMenu()
    }

    override fun showToolbarIcons() {
        showToolbarIcons = true
        activity!!.invalidateOptionsMenu()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadSavedCars()
    }

    /**
     * Registering the EventBus in this lifecycle method because I only want to update
     * the UI when the Fragment is visible to the user
     */
    override fun onStart() {
        super.onStart()
        presenter.register()
    }

    /**
     * Unregistering the EventBus in this lifecycle method because I only want to update
     * the UI when the Fragment is visible to the user
     */
    override fun onStop() {
        super.onStop()
        presenter.unregister()
    }

    private fun getDialogView(): View? = LayoutInflater
            .from(activity)
            .inflate(R.layout.dialog_delete_car, null)


    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false


}
