package com.example.benjo.bil_app_kotlin.base

import android.content.Context

interface BaseView<T> {

    fun showProgess()

    fun hideProgress()

    fun showError()

    fun getContext(): Context

    var presenter: T
}