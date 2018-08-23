package com.example.benjo.bil_app_kotlin.room

import android.os.Handler
import android.os.Looper
import com.example.benjo.bil_app_kotlin.saved.SavedContract

class RoomThread(private val instance: CarDataBase?,
                 private val presenter : SavedContract.Presenter) : Runnable {

    override fun run() {
        val list = instance?.carDataDao()?.getAll()
        if(list != null) {
            val arrayList = arrayListOf<CarData>()
            for(item in list) {
                arrayList.add(CarData(null, item.vin, item.json))
            }
            Handler(Looper.getMainLooper()).post{ presenter.showSavedCars(arrayList) }
        }
    }
}