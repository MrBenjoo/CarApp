package com.example.benjo.bil_app_kotlin.tabview

import android.util.Log
import com.example.benjo.bil_app_kotlin.room.CarData
import com.example.benjo.bil_app_kotlin.room.CarDataBase

class ThreadRoomAdd(val instance: CarDataBase?,
                    val json: String?,
                    val presenter: MainPresenter) : Runnable {

    private val TAG = "ThreadRoomAdd"

    override fun run() {
        val car = CarData(null, 11, json!!)
        val list = instance?.carDataDao()?.getAll()
        var exist = false
        if (list != null) {
            for (item in list) {
                if (item.vin == car.vin) {
                    Log.d(TAG, "cant save car bcuz it already exist")
                    exist = true
                }
            }
        }
        if (!exist) {
            instance?.carDataDao()?.insert(car)
            Log.d(TAG, "car saved")
        }

    }
}