package com.example.benjo.swecar.base


interface BasePresenter<T> {

    fun attachView(view : T)

}