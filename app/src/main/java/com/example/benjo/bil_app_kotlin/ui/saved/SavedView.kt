package com.example.benjo.bil_app_kotlin.ui.saved


import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.content.DialogInterface.BUTTON_POSITIVE
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.ActionMode

import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.MainActivity
import com.example.benjo.bil_app_kotlin.data.room.CarData
import android.view.*
import androidx.navigation.Navigation
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.data.repository.CarRepositoryImpl
import com.example.benjo.bil_app_kotlin.data.room.CarDataBase
import com.example.benjo.bil_app_kotlin.domain.Result
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_saved.*


class SavedView : BaseFragment(), SavedContract.View, ActionMode.Callback, Toolbar.OnMenuItemClickListener {
    private val TAG = "SavedView"
    override lateinit var presenter: SavedContract.Presenter
    private var mActionMode: ActionMode? = null
    private var showToolbarIcons = false


    override fun layoutId(): Int = R.layout.fragment_saved

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

    override fun startActionMode() {
        mActionMode = activity!!.startActionMode(this)
        mActionMode?.title = getString(R.string.title_action_mode)
    }

    override fun setActionModeTitle(title: String) = when (title) {
        "0" -> mActionMode?.title = getString(R.string.title_action_mode)
        else -> mActionMode?.title = title
    }

    override fun finishActionMode() {
        mActionMode?.finish()
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean =
            when (item!!.itemId) {
                R.id.action_delete_saved_view -> {
                    showDialogOnMultipleDeletion()
                    true
                }
                R.id.action_delete_all_saved_view -> {
                    presenter.onEvent(SavedListEvent.OnDeleteAllClickActionMode)
                    true
                }
                else -> false
            }

    override fun showDialogOnMultipleDeletion() {
        AlertDialog.Builder(activity!!, R.style.CustomDialogTheme)
                .setTitle(getString(R.string.title_dialog_saved))
                .setPositiveButton(getString(R.string.dialog_saved_positive_btn)) { dialog, which -> onDialogButtonClick(dialog, which) }
                .setNegativeButton(R.string.dialog_saved_negative_btn) { dialog, which -> onDialogButtonClick(dialog, which) }
                .show()
    }

    private fun onDialogButtonClick(dialog: DialogInterface?, which: Int) {
        if (which == BUTTON_POSITIVE) {
            presenter.onEvent(SavedListEvent.OnDeleteClickActionMode)
        }
        dialog?.cancel()
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
        mActionMode = null
        presenter.onDestroyActionMode()
    }

    override fun onCreateActionMode(mode: android.view.ActionMode, menu: Menu): Boolean {
        mode.menuInflater.inflate(R.menu.menu_saved_view, menu)
        return true
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
                presenter.onEvent(SavedListEvent.OnDeleteAllClickView)
                return true
            }
            R.id.action_delete_saved_view -> {
                presenter.onEvent(SavedListEvent.OnDeleteClickView)
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

    override fun showNumberOfDeletedCars(nbrOfDeletedCars: Int) {
        val text = resources.getString(R.string.saved_list_nbr_deleted_cars, nbrOfDeletedCars)
        showText(text)
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

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false


}
