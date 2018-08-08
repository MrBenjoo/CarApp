package com.example.benjo.bil_app_kotlin.sections.basic

import android.util.Log
import com.example.benjo.bil_app_kotlin.MainPresenter
import com.example.benjo.bil_app_kotlin.base.Contract
import com.example.benjo.bil_app_kotlin.network.json_parsing.BasicInfo
import com.example.benjo.bil_app_kotlin.network.json_parsing.JsonHandler
import com.example.benjo.bil_app_kotlin.network.json_parsing.Result
import com.google.gson.GsonBuilder

class PresenterBasic(val view: Contract.View) : Contract.Presenter {

    private val TAG = "PresenterBasic"

    init {
        view.presenter = this
    }

    override fun search(reg: String?) {
        view.showProgess()
        val response = MainPresenter(view.getContext()).search(reg)
        if (response != null) {
            if (response.isSuccessful) {
                val json = response.body()?.carInfo?.basic?.data.toString()
                update(json)//processResponse(response.body())
            }
            else
                view.showErrorHTTP()
        }
        view.hideProgress()
    }

    /*private fun processResponse(response: Result?) {
        with(GsonBuilder().create()) {
            val mapBasic = fromJson(toJson(response?.carInfo?.basic?.data),
                    HashMap<String, String?>()::class.java)
            updateTab(mapBasic)
        }
    }*/

    private fun updateTab(map: HashMap<String, String?>?) {
        val list = JsonHandler(view.getContext()).basicSection(map)
        if (!list.isEmpty()) view.updateList(list)
    }

    override fun update(json: String?) {
        with(GsonBuilder().create()) {
            Log.d(TAG, "update started...")
            val mapBasic = fromJson(json, HashMap<String, String?>()::class.java)
            val list = JsonHandler(view.getContext()).basicSection(mapBasic)
            if (!list.isEmpty()) {
                Log.d(TAG, "updating... (basic list is NOT empty)")
                view.updateList(list)
            } else {
                Log.d(TAG, "updating... (basic list is NOT empty)")
            }
        }
    }


    private fun testCode() {
        val basic = BasicInfo("Volvo S80", "S80", "I trafik", "Svart", "Personbil", "2003", "2003")
        val gson = GsonBuilder().create()
        val jsonBasic = gson.toJson(basic)
        val mapBasic = gson.fromJson(jsonBasic, HashMap<String, String?>()::class.java)
        updateTab(mapBasic)
    }

}