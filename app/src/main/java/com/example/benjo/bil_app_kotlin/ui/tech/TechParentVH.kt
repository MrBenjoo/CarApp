package com.example.benjo.bil_app_kotlin.ui.tech

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.view_expandable_header.view.*

class TechParentVH(headerView: View?) : RecyclerView.ViewHolder(headerView!!) {
    val rootView = headerView?.expandable_header
    val headerText = headerView?.header_text
    val imgView = headerView?.header_img
}