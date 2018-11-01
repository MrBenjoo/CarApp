package com.example.benjo.bil_app_kotlin.ui.tech


import com.example.benjo.bil_app_kotlin.utils.JsonHandler
import com.example.benjo.bil_app_kotlin.data.model.Result
import com.example.benjo.bil_app_kotlin.ui.tab.TabsContract
import com.google.gson.GsonBuilder


class TechPresenter : TabsContract.TechPresenter {
    private val TAG = "TechPresenter"
    private lateinit var view: TabsContract.ViewTech


    override fun attachView(v: TabsContract.ViewTech) {
        this.view = v
    }

    override fun updateTab(response: Result?) {
        val gson = GsonBuilder().create()
        val jsonStringTech = gson.toJson(response?.carInfo?.technical?.data)
        val mapTech = gson.fromJson(jsonStringTech, HashMap<String, String?>()::class.java)
        updateTechSections(mapTech)
    }

    override fun fromJson(json: String): Result =
            GsonBuilder().create().fromJson(json, Result::class.java)

    private fun updateTechSections(map: HashMap<String, String?>?) {
        with(JsonHandler(view.getContext())) {
            val techSection = techSection(map)
            val dimensionsSection = dimensionsSection(map)
            val otherSection = otherSection(map)
            if (!techSection.isEmpty()) view.updateList("Teknisk data", techSection)
            if (!dimensionsSection.isEmpty()) view.updateList("Dimensioner", dimensionsSection)
            if (!otherSection.isEmpty()) view.updateList("Övrigt", otherSection)
        }
    }

}