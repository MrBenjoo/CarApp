package com.example.benjo.bil_app_kotlin.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.model.Row
import com.example.benjo.bil_app_kotlin.tabs.basic.BasicVH

class AdapterBasicList(data: ArrayList<Row>?) : RecyclerView.Adapter<BasicVH>() {
    private val TAG = "AdapterBasicList"
    private var data: ArrayList<Row>? = null


    init {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_view, parent, false)
        return BasicVH(view)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holderBasic: BasicVH, position: Int) {
        data?.let {
            with(holderBasic) {
                descRow.text = it[position].desc
                dataRow.text = it[position].data
            }
        }
    }

    fun setList(list: ArrayList<Row>) {
        Log.d(TAG, "setList")
        this.data?.clear()
        this.data?.addAll(list)
        notifyDataSetChanged()
    }

}