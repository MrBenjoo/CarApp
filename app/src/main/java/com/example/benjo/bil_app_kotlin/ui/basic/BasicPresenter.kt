package com.example.benjo.bil_app_kotlin.ui.basic


import com.example.benjo.bil_app_kotlin.utils.JsonHandler
import com.example.benjo.bil_app_kotlin.data.model.Result
import com.example.benjo.bil_app_kotlin.data.model.Row
import com.example.benjo.bil_app_kotlin.ui.tab.TabsContract
import com.google.gson.GsonBuilder


class BasicPresenter(val adapter: BasicAdapter) : TabsContract.BasicPresenter {
    private val TAG = "BasicPresenter"
    private lateinit var view: TabsContract.ViewBasic

    override fun attachView(view: TabsContract.ViewBasic) {
        this.view = view
    }

    override fun updateTab(result: Result?) {
        val gson = GsonBuilder().create()
        val jsonStringBasic = gson.toJson(result?.carInfo?.basic?.data)
        val mapBasic = gson.fromJson(jsonStringBasic, HashMap<String, String?>()::class.java)
        val list = JsonHandler(view.getContext()).basicSection(mapBasic)
        if (!list.isEmpty()) updateList(list)
    }

    override fun bind() {
        view.setAdapter(adapter)
    }

    private fun updateList(list: ArrayList<Row>) {
        adapter.setList(list)
    }


}