package com.example.benjo.bil_app_kotlin.network.retrofit

object SearchRegProvider {

    fun provideSearchReg(): SearchReg {
        return SearchReg(BilUppgifterApi.Factory.create())
    }
}