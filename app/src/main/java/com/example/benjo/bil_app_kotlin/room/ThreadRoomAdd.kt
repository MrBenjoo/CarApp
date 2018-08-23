package com.example.benjo.bil_app_kotlin.room

import android.util.Log
import com.example.benjo.bil_app_kotlin.network.json_parsing.Result
import com.example.benjo.bil_app_kotlin.tabview.MainPresenter
import com.google.gson.GsonBuilder

class ThreadRoomAdd(val instance: CarDataBase?,
                    val response: Result?,
                    val presenter: MainPresenter) : Runnable {

    private val TAG = "ThreadRoomAdd"

    override fun run() {
        val gson = GsonBuilder().create()
        val json = gson.toJson(response)
        val car = CarData(null, response?.carInfo?.attributes?.vin!!.toInt(), json!!)
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