package com.example.benjo.swecar.ui.saved


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.benjo.swecar.R
import com.example.benjo.swecar.base.BaseFragment
import com.example.benjo.swecar.servicelocator.PresenterServiceLocator
import com.example.benjo.swecar.utils.*
import kotlinx.android.synthetic.main.fragment_saved.*


class SavedView : BaseFragment(), SavedContract.View, ActionMode.Callback, Toolbar.OnMenuItemClickListener {
    private val TAG = "SavedView"
    override lateinit var presenter: SavedContract.Presenter
    private var mActionMode: ActionMode? = null
    private var showToolbarIcons = false
    private var showingDeleteIcon = false
    private var editMenuItem: MenuItem? = null


    override fun layoutId(): Int = R.layout.fragment_saved

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar_saved, this)
        initPresenter()
        setAdapter(presenter.getListAdapter())
    }

    private fun initPresenter() {
        presenter = PresenterServiceLocator.provideSavedPresenter(mainActivity())
        presenter.attachView(this)
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
                .setTitle(string(R.string.saved_dialog_title))
                .setPositiveButton(string(R.string.saved_dialog_positive_btn)) { dialog, _ ->
                    presenter.onEvent(SavedListEvent.OnDeleteClick)
                    dialog.cancel()
                }
                .setNegativeButton(R.string.saved_dialog_negative_btn) { dialog, _ -> dialog.cancel() }
                .show()
    }


    override fun showDeleteIcon() {
        mActionMode!!.menu.findItem(R.id.action_delete_saved_view).isVisible = true
        showingDeleteIcon = true
    }

    override fun hideDeleteIcon() {
        mActionMode!!.menu.findItem(R.id.action_delete_saved_view).isVisible = false
        showingDeleteIcon = false
    }


    override fun onCreateActionMode(mode: android.view.ActionMode, menu: Menu): Boolean {
        mode.menuInflater.inflate(R.menu.menu_saved_action_mode, menu)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_saved_view, menu)

        editMenuItem = menu.findItem(R.id.action_edit)

        when (showToolbarIcons) {
            true -> editMenuItem?.isVisible = true
            false -> editMenuItem?.isVisible = false
        }
    }

    override fun hideToolbarIcons() {
        showToolbarIcons = false
        editMenuItem?.isVisible = false
    }

    override fun showToolbarIcons() {
        showToolbarIcons = true
        editMenuItem?.isVisible = true
    }

    override fun showNumberOfDeletedCars(nbrOfDeletedCars: Int) {
        showText(string(R.string.saved_list_nbr_deleted_cars, nbrOfDeletedCars))
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
        presenter.onEvent(SavedListEvent.OnDestroy)
    }

    override fun navigateToTabs() = navigate(R.id.tabsFragment)

    override fun isShowingDeleteIcon(): Boolean = showingDeleteIcon

    override fun startActionMode() {
        mActionMode = activity!!.startActionMode(this)
        mActionMode?.title = getString(R.string.saved_action_mode_title)
    }

    override fun onDestroyActionMode(p0: ActionMode?) {
        mActionMode = null
        presenter.onDestroyActionMode()
    }

    override fun setActionModeTitle(title: String) = when (title) {
        "0" -> mActionMode?.title = string(R.string.saved_action_mode_title)
        else -> mActionMode?.title = title
    }

    override fun finishActionMode() {
        mActionMode?.finish()
    }

    override fun setAdapter(savedAdapter: SavedAdapter) {
        with(recycler_view_saved) {
            setHasFixedSize(true)
            adapter = savedAdapter
            addItemDecoration(ItemDivideDecoration(context))
        }
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

}
