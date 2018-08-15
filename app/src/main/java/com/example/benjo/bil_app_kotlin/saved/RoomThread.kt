package com.example.benjo.bil_app_kotlin.saved

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.benjo.bil_app_kotlin.room.CarDataBase
import com.example.benjo.bil_app_kotlin.sections.Row

class RoomThread(private val instance: CarDataBase?,
                 private val presenter : SavedContract.Presenter) : Runnable {

    override fun run() {
        val list = instance?.carDataDao()?.getAll()
        if(list != null) {
            val arrayList = arrayListOf<Row>()
            for(item in list) {
                arrayList.add(Row(item.id.toString(), item.vin.toString()))
            }
            Handler(Looper.getMainLooper()).post{ presenter.showSavedCars(arrayList) }
        }
    }
}