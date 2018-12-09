package com.example.benjo.bil_app_kotlin.data.network


object SearchRegProvider {

    fun provideSearchReg(): SearchReg {
        return SearchReg(BilUppgifterApi.Factory.create())
    }

}