package com.example.benjo.bil_app_kotlin.builder

import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView

class ToolbarManager constructor(
        private var builder: FragmentToolbar,
        private var container: View) {

    fun prepareToolbar() {
        if (builder.resId != FragmentToolbar.NO_TOOLBAR) {
            val fragmentToolbar = container.findViewById(builder.resId) as Toolbar
            container.findViewById(builder.resId) as Toolbar

            if (builder.title != -1) {
                fragmentToolbar.setTitle(builder.title)
            }

            if (builder.menuId != -1) {
                fragmentToolbar.inflateMenu(builder.menuId)
            }

            if (!builder.menuItems.isEmpty() && !builder.menuClicks.isEmpty()) {
                val menu = fragmentToolbar.menu
                for ((index, menuItemId) in builder.menuItems.withIndex()) {
                    (menu.findItem(menuItemId) as MenuItem).setOnMenuItemClickListener(builder.menuClicks[index])
                }
            }
            if (builder.navBackId != -1) {
                Log.d("ToolbarManager", "prepareToolbar -> builder.navBackId != -1")
                val navigation = container.findViewById(builder.navBackId) as? ImageView
                navigation?.setOnClickListener { builder.navBackListener() }
            }
        }
    }
}