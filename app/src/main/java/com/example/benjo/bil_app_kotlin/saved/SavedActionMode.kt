package com.example.benjo.bil_app_kotlin.saved

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import com.example.benjo.bil_app_kotlin.R

class SavedActionMode(private val view: SavedContract.View) : ActionMode.Callback {

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean = when (item!!.itemId) {
        R.id.action_delete -> {
            view.showDialogOnMultipleDeletion()
            true
        }
        else -> false
    }

    override fun onCreateActionMode(mode: android.view.ActionMode, menu: Menu): Boolean {
        mode.menuInflater.inflate(R.menu.saved_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

    override fun onDestroyActionMode(p0: ActionMode?) {
        view.onDestroyActionMode()
    }
}