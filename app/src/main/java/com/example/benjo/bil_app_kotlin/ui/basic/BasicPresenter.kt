package com.example.benjo.bil_app_kotlin.ui.basic


import android.util.Log
import android.util.MalformedJsonException
import com.example.benjo.bil_app_kotlin.data.network.model.BasicInfo
import com.example.benjo.bil_app_kotlin.utils.JsonHandler
import com.example.benjo.bil_app_kotlin.ui.tab.Row
import com.google.gson.GsonBuilder
import com.example.benjo.bil_app_kotlin.data.network.model.SearchResponse


class BasicPresenter(private val adapter: BasicAdapter) : BasicContract.BasicPresenter {
    private val TAG = "BasicPresenter"
    private lateinit var view: BasicContract.ViewBasic

    override fun attachView(view: BasicContract.ViewBasic) {
        this.view = view
    }

    override fun updateTab(searchResponse: SearchResponse?) {
        try {
            processMap(getMapFromResponse(searchResponse?.carInfo?.basic?.data))
        } catch (jsonException: MalformedJsonException) {
            view.showText(jsonException.message)
        }
    }

    private fun processMap(mapFromResponse: HashMap<String, String?>) {
        when (mapFromResponse.isNullOrEmpty()) {
            false -> {
                val list = JsonHandler().removeEmptyValues(mapFromResponse)
                if (!list.isEmpty()) updateList(list)
            }
            true -> Log.d(TAG, "updateTab --> mapBasic isNullOrEmpty")
        }
    }

    private fun getMapFromResponse(data: BasicInfo?): HashMap<String, String?> {
        val gson = GsonBuilder().create()
        val jsonBasic = gson.toJson(data)
        return gson.fromJson(jsonBasic, HashMap<String, String?>()::class.java)
    }

    override fun bind() {
        view.setAdapter(adapter)
    }

    private fun updateList(list: ArrayList<Row>) {
        adapter.setList(list)
    }


}