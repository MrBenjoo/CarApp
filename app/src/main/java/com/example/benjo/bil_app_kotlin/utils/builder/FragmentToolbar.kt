package com.example.benjo.bil_app_kotlin.utils.builder

import android.support.annotation.IdRes
import android.support.annotation.MenuRes
import android.support.annotation.StringRes
import android.view.MenuItem

class FragmentToolbar(@IdRes val resId: Int,
                      @StringRes val title: Int,
                      @MenuRes val menuId: Int,
                      val menuItems: MutableList<Int>,
                      val menuClicks: MutableList<MenuItem.OnMenuItemClickListener?>,
                      val navBackListener: () -> Unit,
                      val navBackId: Int) {

    companion object {
        @JvmField val NO_TOOLBAR = -1
    }

    class Builder {
        private var resId: Int = -1
        private var menuId: Int = -1
        private var title: Int = -1
        private var menuItems: MutableList<Int> = mutableListOf()
        private var menuClicks: MutableList<MenuItem.OnMenuItemClickListener?> = mutableListOf()
        private lateinit var navBackListener: () -> Unit
        private var navBackId: Int = -1

        fun withId(@IdRes resId: Int) = apply { this.resId = resId }

        fun withTitle(title: Int) = apply { this.title = title }

        fun withMenu(@MenuRes menuId: Int) = apply { this.menuId = menuId }

        fun withMenuItems(menuItems: MutableList<Int>, menuClicks: MutableList<MenuItem.OnMenuItemClickListener?>) = apply {
            this.menuItems.addAll(menuItems)
            this.menuClicks.addAll(menuClicks)
        }

        fun withNavBackListener(listener: () -> Unit, navBackId: Int) = apply {
                    this.navBackListener = listener
                    this.navBackId = navBackId
                }


        fun build() = FragmentToolbar(resId, title, menuId, menuItems, menuClicks, navBackListener, navBackId)

    }
}