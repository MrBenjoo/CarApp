package com.example.benjo.bil_app_kotlin.ui.basic


import android.util.Log
import android.util.MalformedJsonException
import com.example.benjo.bil_app_kotlin.domain.JsonHandler
import com.example.benjo.bil_app_kotlin.ui.tab.Row
import com.example.benjo.bil_app_kotlin.ui.tab.TabsContract
import com.google.gson.GsonBuilder
import com.example.benjo.bil_app_kotlin.domain.Result


class BasicPresenter(val adapter: BasicAdapter) : BasicContract.BasicPresenter {
    private val TAG = "BasicPresenter"
    private lateinit var view: BasicContract.ViewBasic

    override fun attachView(view: BasicContract.ViewBasic) {
        this.view = view
    }

    override fun updateTab(result: Result?) {
        val gson = GsonBuilder().create()
        val jsonBasic = gson.toJson(result?.carInfo?.basic?.data)
        try {
            val mapBasic = gson.fromJson(jsonBasic, HashMap<String, String?>()::class.java)
            if (!mapBasic.isNullOrEmpty()) {
                val list = JsonHandler().removeEmptyValues(mapBasic)
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