package com.example.benjo.bil_app_kotlin.domain

object SearchRegProvider {

    fun provideSearchReg(): SearchReg {
        return SearchReg(BilUppgifterApi.Factory.create())
    }
}