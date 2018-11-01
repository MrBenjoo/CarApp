package com.example.benjo.bil_app_kotlin.ui.tech

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.view_row_tabs.view.*

class TechChildVH(childView: View?) : RecyclerView.ViewHolder(childView!!) {
    val descRow = childView?.row_tv_desc
    val dataRow = childView?.row_tv_data
}