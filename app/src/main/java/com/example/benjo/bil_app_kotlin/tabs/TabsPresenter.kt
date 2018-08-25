package com.example.benjo.bil_app_kotlin.tabview

import android.content.Context
import android.util.Log
import com.example.benjo.bil_app_kotlin.room.CarDataBase
import com.example.benjo.bil_app_kotlin.network.Connectivity
import com.example.benjo.bil_app_kotlin.network.json.Result
import com.example.benjo.bil_app_kotlin.network.retrofit.Lambda
import com.example.benjo.bil_app_kotlin.network.retrofit.SearchRegProvider
import com.example.benjo.bil_app_kotlin.room.CarData
import com.google.gson.GsonBuilder
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Response

class MainPresenter(val context : Context){
    private val TAG = "MainPresenter"


    fun search(reg: String?): Response<Result>? {
        var mResponse: Response<Result>? = null
        val connected = Connectivity(context).isConnected()
        if(connected) {
            SearchRegProvider
                    .provideSearchReg()
                    .searchReg(reg)
                    .enqueue(Lambda().callback { throwable, response ->
                        throwable.let { /*onFailure(throwable)*/ }
                        response.let {
                            mResponse = response
                        }
                    })
        }

        return mResponse
    }

    fun saveToDatabase(jsonResponse: Result?) {
        Log.d(TAG, "*************** SAVE IMAGE CLICKED ***************")
        /*Thread(ThreadRoomAdd(CarDataBase.getInstance(context), jsonResponse, this))
                .start()*/
        launch(UI) {
            val car = CarData(null,
                    jsonResponse?.carInfo?.attributes?.vin!!.toInt(),
                    GsonBuilder().create().toJson(jsonResponse))
            var exist = false
            async(CommonPool) {
                val instance = CarDataBase.getInstance(context)
                val list = instance?.carDataDao()?.getAll()
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
    }

}