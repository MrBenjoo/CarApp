package com.example.benjo.bil_app_kotlin.sections.basic

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.row_list.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val descRow = itemView.row_tv_desc
    val dataRow = itemView.row_tv_data
}