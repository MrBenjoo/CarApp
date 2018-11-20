package com.example.benjo.bil_app_kotlin.ui.comparing.renderer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.ui.comparing.constants.Companion.TYPE_COMMONN
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.model.Normalization
import kotlinx.android.synthetic.main.view_compare_row_include.view.*

class CommonHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title = itemView.tv_comparing_title

    val carModelOne = itemView.tv_comparing_model_one
    val carOneData = itemView.tv_compare_row_text_model_one_val
    val barCarOne = itemView.bar_comparing_model_one

    val carModelTwo = itemView.tv_comparing_model_two
    val carTwoData = itemView.tv_comparing_model_two_val
    val barCarTwo = itemView.bar_comparing_model_two
}

data class ComparData(
        val TYPE: Int = TYPE_COMMONN,
        val title: String?,
        val carModelOne: String?,
        val carOneData: String?,
        val carModelTwo: String?,
        val carTwoData: String?
) : ItemModel {

    override fun getType(): Int = TYPE
}

class CompareRowRenderer : Renderer<ComparData, CommonHolder>() {

    override fun createViewHolder(parent: ViewGroup?): CommonHolder {
        return CommonHolder(
                LayoutInflater.from(parent?.context).inflate(
                        R.layout.view_compare_row_include,
                        parent,
                        false
                )
        )
    }

    override fun bindView(model: ComparData, holder: CommonHolder) {
        val carOneData = model.carOneData
        val carTwoData = model.carTwoData

        holder.title.text = model.title ?: "N/A"
        holder.carModelOne.text = model.carModelOne ?: "NA"
        holder.carOneData.text = model.carOneData ?: "NA"
        holder.carModelTwo.text = model.carModelTwo ?: "NA"
        holder.carTwoData.text = model.carTwoData ?: "NA"

        if (carOneData != null || carTwoData != null) {
            holder.barCarOne.progress = Normalization(model.title!!, carOneData!!).perform()
            holder.barCarTwo.progress = Normalization(model.title, carTwoData!!).perform()
        }
    }

}