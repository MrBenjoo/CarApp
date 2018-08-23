package com.example.benjo.bil_app_kotlin.room

import android.os.Handler
import android.os.Looper
import com.example.benjo.bil_app_kotlin.saved.SavedContract

class RoomGetCar(private val instance: CarDataBase?,
                 private val presenter : SavedContract.Presenter,
                 private val vin: Int) : Runnable {

    override fun run() {
        val car = instance?.carDataDao()?.getCar(vin)
        if(car != null) {
            Handler(Looper.getMainLooper()).post{ presenter.showCar(car) }
        }
    }

}