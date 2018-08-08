package com.example.benjo.bil_app_kotlin.sections.tech

import android.util.Log
import com.example.benjo.bil_app_kotlin.MainPresenter
import com.example.benjo.bil_app_kotlin.base.Contract
import com.example.benjo.bil_app_kotlin.network.json_parsing.JsonHandler
import com.example.benjo.bil_app_kotlin.network.json_parsing.Result
import com.example.benjo.bil_app_kotlin.network.json_parsing.TechnicalData
import com.google.gson.GsonBuilder

class PresenterTech(val view: Contract.ViewTech) : Contract.Presenter {


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
                view.showErrorHTTP()
        }
        view.hideProgress()
    }

     private fun processResponse(response: Result?) {
        with(GsonBuilder().create()) {
            val mapTech = fromJson(toJson(response?.carInfo?.technical?.data),
                    HashMap<String, String?>()::class.java)
            updateTab(mapTech)
        }
    }

    override fun update(json: String?) {
        with(GsonBuilder().create()) {
            updateTab(fromJson(json, HashMap<String, String?>()::class.java))
        }
    }

     private fun updateTab(map: HashMap<String, String?>?) {
        with(JsonHandler(view.getContext())) {
            val techSection = techSection(map)
            val dimensionsSection = dimensionsSection(map)
            val otherSection = otherSection(map)
            if (!techSection.isEmpty()) view.updateList("Teknisk data", techSection)
            if (!dimensionsSection.isEmpty()) view.updateList("Dimensioner", dimensionsSection)
            if (!otherSection.isEmpty()) view.updateList("Övrigt", otherSection)
        }
    }



    private fun testCode() {
        val tehnical = TechnicalData("180", "230", "1798 ", "200 ", "Bensin / Etanol", "7.4",
                "177", "manuell ", "ja", "71", "4", "ja", "fan vet jag",
                "1500", "Herrgårdsvagn", "3000", "1500", "2000", "3000", "4000",
                "5100", "4000", "6000", "1610", "2360", "1300",
                "205/50 R17 93W", "205/50 R17 93W", "7Jx17x52,5", "7Jx17x52,5", "axel_width", "M1", null/*"e4*2001/116*0076*13"*/, "nox_1",
                null, "2005", "emission_class", null)
        val gson = GsonBuilder().create()
        val jsonBasic = gson.toJson(tehnical)
        val mapBasic = gson.fromJson(jsonBasic, HashMap<String, String?>()::class.java)
        updateTab(mapBasic)
    }


}