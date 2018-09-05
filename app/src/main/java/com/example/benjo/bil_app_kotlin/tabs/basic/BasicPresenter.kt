package com.example.benjo.bil_app_kotlin.tabs.basic


import com.example.benjo.bil_app_kotlin.utils.JsonHandler
import com.example.benjo.bil_app_kotlin.data.model.Result
import com.example.benjo.bil_app_kotlin.tabs.TabsContract
import com.google.gson.GsonBuilder

class BasicPresenter : TabsContract.BasicPresenter {
    private val TAG = "BasicPresenter"
    private lateinit var view: TabsContract.ViewBasic

    override fun attachView(v: TabsContract.ViewBasic) {
        this.view = v
    }

    override fun fromJson(json: String): Result =
            GsonBuilder().create().fromJson(json, Result::class.java)

    override fun updateTab(response: Result?) {
        val gson = GsonBuilder().create()
        val jsonStringBasic = gson.toJson(response?.carInfo?.basic?.data)
        val mapBasic = gson.fromJson(jsonStringBasic, HashMap<String, String?>()::class.java)
        val list = JsonHandler(view.getContext()).basicSection(mapBasic)
        if (!list.isEmpty()) view.updateList(list)
    }


}