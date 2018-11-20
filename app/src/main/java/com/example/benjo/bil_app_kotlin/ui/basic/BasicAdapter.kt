package com.example.benjo.bil_app_kotlin.ui.basic

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.model.Row
import kotlinx.android.synthetic.main.item_tabs_row.view.*

class BasicAdapter(val data: ArrayList<Row>?) : RecyclerView.Adapter<BasicAdapter.BasicViewHolder>() {
    private val TAG = "BasicAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BasicViewHolder(
                inflater.inflate(R.layout.item_tabs_row, parent, false)
        )
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holderBasic: BasicViewHolder, position: Int) {
        data?.let {
            with(holderBasic) {
                descRow.text = it[position].desc
                dataRow.text = it[position].data
            }
        }
    }

    fun setList(list: ArrayList<Row>) {
        this.data?.clear()
        this.data?.addAll(list)
        notifyDataSetChanged()
    }

    class BasicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descRow = itemView.row_tv_desc
        val dataRow = itemView.row_tv_data
    }

}