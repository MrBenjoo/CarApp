package com.example.benjo.bil_app_kotlin.ui.basic


import android.util.Log
import android.util.MalformedJsonException
import com.example.benjo.bil_app_kotlin.utils.JsonHandler
import com.example.benjo.bil_app_kotlin.data.model.Row
import com.example.benjo.bil_app_kotlin.ui.tab.TabsContract
import com.google.gson.GsonBuilder
import com.example.benjo.bil_app_kotlin.domain.Result
import java.lang.Exception


class BasicPresenter(val adapter: BasicAdapter) : TabsContract.BasicPresenter {
    private val TAG = "BasicPresenter"
    private lateinit var view: TabsContract.ViewBasic

    override fun attachView(view: TabsContract.ViewBasic) {
        this.view = view
    }

    override fun updateTab(result: Result?) {
        val gson = GsonBuilder().create()
        val jsonStringBasic = gson.toJson(result?.carInfo?.basic?.data)
        Log.d(TAG, "updateTab --> make: " + result?.carInfo?.basic?.data?.make + " color: " + result?.carInfo?.basic?.data?.color)
        try {
            val mapBasic = gson.fromJson(jsonStringBasic, HashMap<String, String?>()::class.java)
            if (!mapBasic.isNullOrEmpty()) {
                val list = JsonHandler().basicSection(mapBasic)
                if (!list.isEmpty()) updateList(list)
            } else {
                Log.d(TAG, "updateTab --> mapBasic isNullOrEmpty")
            }
        } catch (jsonException: MalformedJsonException) {
            view.showText(jsonException.message)
        }
    }

    override fun bind() {
        view.setAdapter(adapter)
    }

    private fun updateList(list: ArrayList<Row>) {
        adapter.setList(list)
    }


}