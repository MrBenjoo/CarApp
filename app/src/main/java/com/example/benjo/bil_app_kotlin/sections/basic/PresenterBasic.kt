package com.example.benjo.bil_app_kotlin.sections.basic

import com.example.benjo.bil_app_kotlin.MainPresenter
import com.example.benjo.bil_app_kotlin.base.Contract
import com.example.benjo.bil_app_kotlin.network.json_parsing.BasicInfo
import com.example.benjo.bil_app_kotlin.network.json_parsing.JsonHandler
import com.example.benjo.bil_app_kotlin.network.json_parsing.Result
import com.google.gson.GsonBuilder

class PresenterBasic(val view: Contract.View) : Contract.Presenter {

    init {
        view.presenter = this
    }

    override fun search(reg: String?) {
        view.showProgess()
        val response = MainPresenter(view.getContext()).search(reg)
        if (response != null) {
            if (response.isSuccessful)
                processResponse(response.body())
            else
                view.showError()
        }
        view.hideProgress()
    }

    override fun processResponse(response: Result?) {
        with(GsonBuilder().create()) {
            val mapBasic = fromJson(toJson(response?.carInfo?.basic?.data),
                    HashMap<String, String?>()::class.java)
            updateTab(mapBasic)
        }
    }

    override fun updateTab(map: HashMap<String, String?>?) {
        val list = JsonHandler(view.getContext()).basicSection(map)
        if (!list.isEmpty()) view.updateList(list)
    }



    private fun testCode() {
        val basic = BasicInfo("Volvo S80", "S80", "I trafik", "Svart", "Personbil", "2003", "2003")
        val gson = GsonBuilder().create()
        val jsonBasic = gson.toJson(basic)
        val mapBasic = gson.fromJson(jsonBasic, HashMap<String, String?>()::class.java)
        updateTab(mapBasic)
    }

}