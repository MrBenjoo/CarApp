package com.example.benjo.bil_app_kotlin.sections.tech

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.row_list.view.*

class ChildViewHolder(childView: View?) : RecyclerView.ViewHolder(childView) {
    val descRow = childView?.row_tv_desc
    val dataRow = childView?.row_tv_data
}