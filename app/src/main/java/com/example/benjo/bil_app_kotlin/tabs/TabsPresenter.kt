package com.example.benjo.bil_app_kotlin.tabs

import android.content.Context
import android.util.Log
import com.example.benjo.bil_app_kotlin.room.CarDataBase
import com.example.benjo.bil_app_kotlin.network.Connectivity
import com.example.benjo.bil_app_kotlin.network.json.Result
import com.example.benjo.bil_app_kotlin.network.retrofit.Lambda
import com.example.benjo.bil_app_kotlin.network.retrofit.SearchRegProvider
import com.example.benjo.bil_app_kotlin.room.CarData
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Response

class TabsPresenter(val context: Context) {
    private val TAG = "TabsPresenter"


    fun search(reg: String?): Response<Result>? {
        var mResponse: Response<Result>? = null
        val connected = Connectivity(context).isConnected()
        if (connected) {
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

    fun saveToDatabase(vin: Int, jsonResponse: String) {
        val car = CarData(null, vin, jsonResponse)

        launch(UI) {
            async(CommonPool) {
                val instance = CarDataBase.getInstance(context)
                val list = instance?.carDataDao()?.getAll()
                val exist = checkIfCarExist(list, vin)
                if (exist)
                    Log.d(TAG, "saveToDatabase -> car was not saved")
                else
                    instance?.carDataDao()?.insert(car)
            }
        }
    }

    private fun checkIfCarExist(list: List<CarData>?, vin: Int): Boolean {
        if (list != null) {
            for (item in list) {
                if (item.vin == vin) {
                    return true
                }
            }
        }
        return false
    }


}