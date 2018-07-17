package com.example.benjo.bil_app_kotlin.sections.tech

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.header_list.view.*

class ParentViewHolder(headerView: View?) : RecyclerView.ViewHolder(headerView) {
    val headerText = headerView?.header_list_text
    val rootView = headerView?.rootView_header
    val imgView = headerView?.header_list_img
}