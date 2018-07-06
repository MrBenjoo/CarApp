package com.example.benjo.bil_app_kotlin.list.ordinary

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.list.Row
import kotlinx.android.synthetic.main.row_list.view.*

class ListAdapter(data: ArrayList<Row>?) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private var data: ArrayList<Row>? = null

    init {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_list, parent, false)
        return ViewHolder(view);
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data?.let {
            with(holder) {
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descRow = itemView.row_tv_desc
        val dataRow = itemView.row_tv_data
    }
}