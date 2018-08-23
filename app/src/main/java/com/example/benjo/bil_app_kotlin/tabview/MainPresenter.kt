package com.example.benjo.bil_app_kotlin.tabview

import android.content.Context
import android.util.Log
import com.example.benjo.bil_app_kotlin.room.CarDataBase
import com.example.benjo.bil_app_kotlin.network.Connectivity
import com.example.benjo.bil_app_kotlin.network.json_parsing.BasicInfo
import com.example.benjo.bil_app_kotlin.network.json_parsing.CarInfo
import com.example.benjo.bil_app_kotlin.network.json_parsing.Result
import com.example.benjo.bil_app_kotlin.network.retrofit.Lambda
import com.example.benjo.bil_app_kotlin.network.retrofit.SearchRegProvider
import com.example.benjo.bil_app_kotlin.room.ThreadRoomAdd
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
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
        //val basic = BasicInfo("Volvo S80", "S80", "I trafik", "Svart", "Personbil", "2003", "2003")
        //val gson = GsonBuilder().create()
        //val jsonBasic = gson.toJson(basic)
        Thread(ThreadRoomAdd(CarDataBase.getInstance(context), jsonResponse, this))
                .start()
    }

}