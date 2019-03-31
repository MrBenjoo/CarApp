package com.example.benjo.swecar.ui.basic


import android.util.Log
import android.util.MalformedJsonException
import com.example.benjo.swecar.data.network.model.BasicInfo
import com.example.benjo.swecar.data.network.model.SearchResponse
import com.example.benjo.swecar.MainViewModel
import com.example.benjo.swecar.ui.tab.Row
import com.example.benjo.swecar.utils.JsonHandler
import com.google.gson.GsonBuilder


class BasicPresenter(private val adapter: BasicAdapter,
                     private val viewModel: MainViewModel) : BasicContract.Presenter {
    private val TAG = "Presenter"
    private lateinit var view: BasicContract.View

    override fun attachView(view: BasicContract.View) {
        this.view = view
    }

    override fun updateTab(searchResponse: SearchResponse?) {
        try {
            processMap(getMapFromResponse(searchResponse?.carInfo?.basic?.data))
        } catch (jsonException: MalformedJsonException) {
            view.showTextMalformedJson()
        }
    }

    override fun updateTabTest() {
        try {
            processMap(getMapFromResponse( viewModel.getSearchResponse().carInfo?.basic?.data))
        } catch (jsonException: MalformedJsonException) {
            view.showTextMalformedJson()
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