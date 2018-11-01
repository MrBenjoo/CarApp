package com.example.benjo.bil_app_kotlin.ui.comparing

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.view_row_common.view.*

class CommonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
    val title = itemView.tv_comparing_title

    val carModelOne = itemView.tv_comparing_model_one
    val carOneData = itemView.tv_comparing_model_one_val
    val barCarOne = itemView.bar_comparing_model_one

    val carModelTwo = itemView.tv_comparing_model_two
    val carTwoData = itemView.tv_comparing_model_two_val
    val barCarTwo = itemView.bar_comparing_model_two
}