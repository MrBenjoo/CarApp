package com.example.benjo.bil_app_kotlin.servicelocator

import android.arch.lifecycle.ViewModelProviders
import com.example.benjo.bil_app_kotlin.MainActivity
import com.example.benjo.bil_app_kotlin.data.db.CarDataBase
import com.example.benjo.bil_app_kotlin.data.db.repository.CarRepositoryImpl
import com.example.benjo.bil_app_kotlin.ui.compare.CompareViewModel
import com.example.benjo.bil_app_kotlin.ui.tab.TabsContract
import com.example.benjo.bil_app_kotlin.ui.tab.TabsPresenter

object PresenterServiceLocator {

    fun provideTabsPresenter(activity : MainActivity, view : TabsContract.ViewTabs) : TabsContract.TabsPresenter {
        return TabsPresenter(view,
                CarRepositoryImpl(CarDataBase.getInstance(activity.applicationContext)!!),
                CarServiceLocator.provideCarService(),
                ViewModelProviders.of(activity).get(CompareViewModel::class.java))
    }

}