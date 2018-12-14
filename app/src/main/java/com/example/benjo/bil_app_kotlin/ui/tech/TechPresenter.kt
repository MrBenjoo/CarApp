package com.example.benjo.bil_app_kotlin.ui.tech


import android.util.Log
import android.util.MalformedJsonException
import com.example.benjo.bil_app_kotlin.utils.JsonHandler
import com.example.benjo.bil_app_kotlin.data.network.model.Result
import com.example.benjo.bil_app_kotlin.ui.tab.Row
import com.google.gson.GsonBuilder
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter


class TechPresenter(val adapter: SectionedRecyclerViewAdapter) : TechContract.TechPresenter {
    private val TAG = "TechPresenter"
    private lateinit var view: TechContract.ViewTech


    override fun attachView(view: TechContract.ViewTech) {
        this.view = view
    }

    override fun bind() {
        view.setAdapter(adapter)
    }

    override fun updateTab(result: Result?) {
        try {
            val gson = GsonBuilder().create()
            val jsonStringTech = gson.toJson(result?.carInfo?.technical?.data)
            val mapTech = gson.fromJson(jsonStringTech, HashMap<String, String?>()::class.java)
            if (!mapTech.isNullOrEmpty()) {
                updateTechSections(mapTech)
            } else {
                Log.d(TAG, "updateTab -> mapTech.isNullOrEmpty()")
            }
        } catch (jsonException: MalformedJsonException) {
            view.showText(jsonException.message)
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