package com.example.benjo.bil_app_kotlin.ui.saved


import android.content.DialogInterface
import android.content.DialogInterface.BUTTON_POSITIVE
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import android.view.ActionMode

import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.MainActivity
import com.example.benjo.bil_app_kotlin.data.db.model.CarData
import android.view.*
import androidx.navigation.Navigation
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.data.network.model.SearchResponse
import com.example.benjo.bil_app_kotlin.servicelocator.PresenterServiceLocator
import com.example.benjo.bil_app_kotlin.utils.mainActivity
import com.example.benjo.bil_app_kotlin.utils.showText
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_saved.*


class SavedView : BaseFragment(), SavedContract.View, ActionMode.Callback, Toolbar.OnMenuItemClickListener {
    private val TAG = "SavedView"
    override lateinit var presenter: SavedContract.Presenter
    private var mActionMode: ActionMode? = null
    private var showToolbarIcons = false
    private var showingDeleteIcon = false
    private var edit: MenuItem? = null


    override fun layoutId(): Int = R.layout.fragment_saved

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initPresenter()
        setAdapter(presenter.getListAdapter())
    }

    private fun initToolbar() {
        (activity as MainActivity).setSupportActionBar(toolbar_saved)
        (activity as MainActivity).supportActionBar?.title = null
        toolbar_saved.setNavigationOnClickListener { activity?.onBackPressed() }
        toolbar_saved.setOnMenuItemClickListener(this)
    }

    private fun initPresenter() {
        presenter = PresenterServiceLocator.provideSavedPresenter(mainActivity())
        presenter.attachView(this)
    }

    override fun startActionMode() {
        mActionMode = activity!!.startActionMode(this)
        mActionMode?.title = getString(R.string.saved_action_mode_title)
    }

    override fun setActionModeTitle(title: String) = when (title) {
        "0" -> mActionMode?.title = getString(R.string.saved_action_mode_title)
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
                    presenter.onEvent(SavedListEvent.OnSelectAllClick)
                    true
                }
                else -> false
            }

    override fun showDialogOnMultipleDeletion() {
        AlertDialog.Builder(activity!!, R.style.CustomDialogTheme)
                .setTitle(getString(R.string.saved_dialog_title))
                .setPositiveButton(getString(R.string.saved_dialog_positive_btn)) { dialog, which -> onDialogButtonClick(dialog, which) }
                .setNegativeButton(R.string.saved_dialog_negative_btn) { dialog, which -> onDialogButtonClick(dialog, which) }
                .show()
    }

    private fun onDialogButtonClick(dialog: DialogInterface?, which: Int) {
        if (which == BUTTON_POSITIVE) {
            presenter.onEvent(SavedListEvent.OnDeleteClick)
        }
        dialog?.cancel()
    }

    override fun setAdapter(savedAdapter: SavedAdapter) {
        with(recycler_view_saved) {
            setHasFixedSize(true)
            adapter = savedAdapter
            addItemDecoration(ItemDivideDecoration(context))
        }
    }

    override fun showCar(car: CarData) {
        (activity as MainActivity).searchResponseCar1 = GsonBuilder().create().fromJson(car.json, SearchResponse::class.java)
        Navigation.findNavController(this.view!!).navigate(R.id.tabsFragment)
    }

    override fun onDestroyActionMode(p0: ActionMode?) {
        mActionMode = null
        presenter.onDestroyActionMode()
    }

    override fun showDeleteIcon() {
        mActionMode!!.menu.findItem(R.id.action_delete_saved_view).isVisible = true
        showingDeleteIcon = true
    }

    override fun hideDeleteIcon() {
        mActionMode!!.menu.findItem(R.id.action_delete_saved_view).isVisible = false
        showingDeleteIcon = false
    }

    override fun isShowingDeleteIcon(): Boolean = showingDeleteIcon

    override fun onCreateActionMode(mode: android.view.ActionMode, menu: Menu): Boolean {
        mode.menuInflater.inflate(R.menu.menu_saved_action_mode, menu)
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_saved_view, menu)

        edit = menu.findItem(R.id.action_edit)

        when (showToolbarIcons) {
            true -> edit?.isVisible = true
            false -> edit?.isVisible = false
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_edit -> {
                presenter.onEvent(SavedListEvent.OnEditClick)
                return true
            }
        }
        return false
    }

    override fun hideToolbarIcons() {
        showToolbarIcons = false
        edit?.isVisible = false
    }

    override fun showToolbarIcons() {
        showToolbarIcons = true
        edit?.isVisible = true
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

    override fun onDestroy() {
        super.onDestroy()
        presenter.unBind()
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false


}
