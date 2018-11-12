package com.example.benjo.bil_app_kotlin.base


interface BasePresenter<T> {

    fun attachView(view : T)

}