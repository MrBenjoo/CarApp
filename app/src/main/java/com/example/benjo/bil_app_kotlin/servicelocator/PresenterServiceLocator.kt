package com.example.benjo.bil_app_kotlin.servicelocator

import androidx.lifecycle.ViewModelProviders
import com.example.benjo.bil_app_kotlin.MainActivity
import com.example.benjo.bil_app_kotlin.data.db.RoomDb
import com.example.benjo.bil_app_kotlin.data.db.repository.CarRepositoryImpl
import com.example.benjo.bil_app_kotlin.ui.compare.CompareViewModel
import com.example.benjo.bil_app_kotlin.ui.saved.SavedAdapter
import com.example.benjo.bil_app_kotlin.ui.saved.SavedContract
import com.example.benjo.bil_app_kotlin.ui.saved.SavedPresenter
import com.example.benjo.bil_app_kotlin.ui.tab.TabsContract
import com.example.benjo.bil_app_kotlin.ui.tab.TabsPresenter

object PresenterServiceLocator {

    fun provideTabsPresenter(activity: MainActivity, view: TabsContract.View): TabsContract.Presenter {
        return TabsPresenter(view,
                CarRepositoryImpl(RoomDb.getInstance(activity.applicationContext)!!.carDataDao()),
                CarServiceLocator.provideCarService(),
                ViewModelProviders.of(activity).get(CompareViewModel::class.java))
    }

    fun provideSavedPresenter(activity: MainActivity): SavedContract.Presenter {
        return SavedPresenter(
                CarRepositoryImpl(
                        RoomDb.getInstance(activity.applicationContext)!!.carDataDao()),
                SavedAdapter(arrayListOf())
        )
    }

}