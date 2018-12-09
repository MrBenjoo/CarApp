package com.example.benjo.bil_app_kotlin.base

import android.content.Context

interface BaseView<T> {

    var presenter: T

    fun getContext(): Context
    fun showText(text: String?)
    fun showText(textID: Int)

}