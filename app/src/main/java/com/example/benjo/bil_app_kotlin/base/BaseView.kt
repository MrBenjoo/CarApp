package com.example.benjo.bil_app_kotlin.base

import android.content.Context

interface BaseView<T> {

    fun showProgess()

    fun hideProgress()

    fun showErrorHTTP()

    fun getContext(): Context

    var presenter: T
}