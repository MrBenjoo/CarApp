package com.example.benjo.bil_app_kotlin.tabs

import com.example.benjo.bil_app_kotlin.room.CarDataBase
import com.example.benjo.bil_app_kotlin.network.ConnectivityStatus
import com.example.benjo.bil_app_kotlin.network.json.Result
import com.example.benjo.bil_app_kotlin.network.retrofit.Lambda
import com.example.benjo.bil_app_kotlin.network.retrofit.SearchRegProvider
import com.example.benjo.bil_app_kotlin.room.CarData
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import retrofit2.Response

class TabsPresenter(val view: TabsContract.ViewTabs) : TabsContract.TabsPresenter {
    private val TAG = "TabsPresenter"

    override fun search(reg: String?): Response<Result>? {
        var mResponse: Response<Result>? = null
        val connected = ConnectivityStatus(view.getContext()).isConnected()
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

    override suspend fun saveToDatabase(vin: Int, jsonResponse: String): Boolean {
        var saved = false
        //runBlocking {
            saved = async(CommonPool) { insertCar(vin, jsonResponse) }.await()
        //}
        return saved
    }

    /*private*/ fun insertCar(vin: Int, jsonResponse: String): Boolean {
        val car = CarData(null, vin, jsonResponse)
        val instance = CarDataBase.getInstance(view.getContext())
        val exist = checkIfCarExist(instance, vin)
        if (!exist) instance?.carDataDao()?.insert(car)
        return !exist
    }

    /*private*/ fun checkIfCarExist(instance: CarDataBase?, vin: Int): Boolean {
        val list = instance?.carDataDao()?.getAll()
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