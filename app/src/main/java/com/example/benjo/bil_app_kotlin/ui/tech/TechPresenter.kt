package com.example.benjo.bil_app_kotlin.ui.tech


import android.util.Log
import android.util.MalformedJsonException
import com.example.benjo.bil_app_kotlin.utils.JsonHandler
import com.example.benjo.bil_app_kotlin.data.network.model.SearchResponse
import com.example.benjo.bil_app_kotlin.data.network.model.TechnicalInfo
import com.example.benjo.bil_app_kotlin.ui.tab.Row
import com.google.gson.GsonBuilder
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter


class TechPresenter(private val adapter: SectionedRecyclerViewAdapter) : TechContract.Presenter {
    private val TAG = "Presenter"
    private lateinit var view: TechContract.View


    override fun attachView(view: TechContract.View) {
        this.view = view
    }

    override fun bind() {
        view.setAdapter(adapter)
    }

    override fun updateTab(searchResponse: SearchResponse?) {
        try {
            processMap(getMapFromResponse(searchResponse?.carInfo?.technical?.data))
        } catch (jsonException: MalformedJsonException) {
            view.showTextMalformedJson()
        }
    }

    private fun getMapFromResponse(data: TechnicalInfo?): HashMap<String, String?> {
        val gson = GsonBuilder().create()
        val jsonTech = gson.toJson(data)
        return gson.fromJson(jsonTech, HashMap<String, String?>()::class.java)
    }

    private fun processMap(mapFromResponse: HashMap<String, String?>) {
        when (mapFromResponse.isNullOrEmpty()) {
            false -> updateTechSections(mapFromResponse)
            true -> Log.d(TAG, "updateTab --> mapBasic isNullOrEmpty")
        }
    }

    private fun updateTechSections(map: HashMap<String, String?>) {
        with(JsonHandler()) {
            val techSection = technicalSectionList(map)
            val dimensionsSection = dimensionsSectionList(map)
            val otherSection = otherSectionList(map)
            adapter.removeAllSections()
            if (!techSection.isEmpty()) updateList(view.getTechnicalTitle(), techSection)
            if (!dimensionsSection.isEmpty()) updateList(view.getDimensionsTitle(), dimensionsSection)
            if (!otherSection.isEmpty()) updateList(view.getOtherTitle(), otherSection)
        }
    }

    private fun updateList(title: String, list: ArrayList<Row>) {
        adapter.addSection(TechAdapter(title, list, adapter))
        adapter.notifyDataSetChanged()
    }

}