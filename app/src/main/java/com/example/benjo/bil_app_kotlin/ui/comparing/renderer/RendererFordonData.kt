package com.example.benjo.bil_app_kotlin.ui.comparing.renderer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.ui.comparing.constants
import kotlinx.android.synthetic.main.view_compare_row_text_include.view.*

class FordonDataRenderer : Renderer<FordonModel, FordonViewHolder>() {

    override fun createViewHolder(parent: ViewGroup?): FordonViewHolder {
        return FordonViewHolder(
                LayoutInflater.from(parent?.context).inflate(
                        R.layout.view_compare_row_text_include,
                        parent,
                        false
                )
        )
    }

    override fun bindView(model: FordonModel, holder: FordonViewHolder) {
        with(holder) {
            title.text = model.title
            carModelOne.text = model.carModelOne
            carOneData.text = model.carOneData
            carModelTwo.text = model.carModelTwo
            carTwoData.text = model.carTwoData
        }
    }
}

data class FordonModel(
        val TYPE: Int = constants.TYPE_FORDONS_DATA,
        val title: String?,
        val carModelOne: String?,
        val carOneData: String?,
        val carModelTwo: String?,
        val carTwoData: String?
) : ItemModel {
    override fun getType(): Int = TYPE
}


class FordonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title = itemView.tv_compare_row_text_title
    val carModelOne = itemView.tv_compare_row_text_model_one
    val carOneData = itemView.tv_compare_row_text_model_one_val
    val carModelTwo = itemView.tv_compare_row_text_model_two
    val carTwoData = itemView.tv_compare_row_text_model_two_val
}


