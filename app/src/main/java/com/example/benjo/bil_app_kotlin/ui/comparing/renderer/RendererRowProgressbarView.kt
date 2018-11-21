package com.example.benjo.bil_app_kotlin.ui.comparing.renderer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.ui.comparing.Normalization
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.RENDERER_TYPE_COMMON
import kotlinx.android.synthetic.main.view_compare_row_include.view.*


class RendererRowProgressbarView : Renderer<CompareData, CommonHolder>() {

    override fun createViewHolder(parent: ViewGroup?): CommonHolder {
        return CommonHolder(
                LayoutInflater.from(parent?.context).inflate(
                        R.layout.view_compare_row_include,
                        parent,
                        false
                )
        )
    }

    override fun bindView(model: CompareData, holder: CommonHolder) {
        val carOneData = model.carOneData
        val carTwoData = model.carTwoData

        holder.title.text = model.title
        holder.carOneModel.text = model.carOneModel ?: "NA"
        holder.carOneData.text = model.carOneData ?: "NA"
        holder.carTwoModel.text = model.carTwoModel ?: "NA"
        holder.carTwoData.text = model.carTwoData ?: "NA"

        if (carOneData != null) {
            holder.barCarOne.progress = Normalization(holder.title.text.toString(), carOneData).perform()
        }

        if (carTwoData != null) {
            holder.barCarTwo.progress = Normalization(holder.title.text.toString(), carTwoData).perform()
        }
    }

}

data class CompareData(
        val TYPE: Int = RENDERER_TYPE_COMMON,
        val title: String,
        val carOneModel: String?,
        val carOneData: String?,
        val carTwoModel: String?,
        val carTwoData: String?
) : ItemModel {

    override fun getType(): Int = TYPE
}

class CommonHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title = itemView.tv_comparing_title

    val carOneModel = itemView.tv_comparing_model_one
    val carOneData = itemView.tv_compare_row_text_model_one_val
    val barCarOne = itemView.bar_comparing_model_one

    val carTwoModel = itemView.tv_comparing_model_two
    val carTwoData = itemView.tv_comparing_model_two_val
    val barCarTwo = itemView.bar_comparing_model_two
}

