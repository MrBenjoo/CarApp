package com.example.benjo.swecar.servicelocator

import androidx.lifecycle.ViewModelProviders
import com.example.benjo.swecar.MainActivity
import com.example.benjo.swecar.data.db.RoomDb
import com.example.benjo.swecar.data.db.repository.CarRepositoryImpl
import com.example.benjo.swecar.MainViewModel
import com.example.benjo.swecar.ui.saved.SavedAdapter
import com.example.benjo.swecar.ui.saved.SavedContract
import com.example.benjo.swecar.ui.saved.SavedPresenter
import com.example.benjo.swecar.ui.tab.TabsContract
import com.example.benjo.swecar.ui.tab.TabsPresenter

object PresenterServiceLocator {

    fun provideTabsPresenter(activity: MainActivity, view: TabsContract.View): TabsContract.Presenter {
        return TabsPresenter(view,
                CarRepositoryImpl(RoomDb.getInstance(activity.applicationContext)!!.carDataDao()),
                CarServiceLocator.provideCarService(),
                ViewModelProviders.of(activity).get(MainViewModel::class.java))
    }

    fun provideSavedPresenter(activity: MainActivity): SavedContract.Presenter {
        return SavedPresenter(
                CarRepositoryImpl(
                        RoomDb.getInstance(activity.applicationContext)!!.carDataDao()),
                SavedAdapter(arrayListOf()),
                ViewModelProviders.of(activity).get(MainViewModel::class.java)
        )
    }

}