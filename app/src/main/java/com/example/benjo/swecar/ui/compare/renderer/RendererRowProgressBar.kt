package com.example.benjo.swecar.ui.compare.renderer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.swecar.R
import com.example.benjo.swecar.data.calculate
import com.example.benjo.swecar.ui.compare.data.Normalization
import com.example.benjo.swecar.ui.compare.data.model.CommonCompareModel
import kotlinx.android.synthetic.main.view_compare_row_include.view.*


class RendererRowProgressbarView : Renderer<CommonCompareModel, CommonHolder>() {

    override fun createViewHolder(parent: ViewGroup?): CommonHolder {
        return CommonHolder(
                LayoutInflater.from(parent?.context).inflate(
                        R.layout.view_compare_row_include,
                        parent,
                        false
                )
        )
    }

    override fun bindView(model: CommonCompareModel, holder: CommonHolder) {
        val carOneData = model.carOneData
        val carTwoData = model.carTwoData

        holder.title.text = model.title
        holder.carOneModel.text = model.carOneModel ?: "NA"
        holder.carOneData.text = model.carOneData ?: "NA"
        holder.carTwoModel.text = model.carTwoModel ?: "NA"
        holder.carTwoData.text = model.carTwoData ?: "NA"

        if (carOneData != null) {
            holder.barCarOne.progress = Normalization(holder.title.text.toString(), carOneData).calculate()
        }

        if (carTwoData != null) {
            holder.barCarTwo.progress = Normalization(holder.title.text.toString(), carTwoData).calculate()
        }
    }

}

class CommonHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    val title = itemView.tv_comparing_title

    val carOneModel = itemView.tv_comparing_model_one
    val carOneData = itemView.tv_compare_row_text_model_one_val
    val barCarOne = itemView.bar_comparing_model_one

    val carTwoModel = itemView.tv_comparing_model_two
    val carTwoData = itemView.tv_comparing_model_two_val
    val barCarTwo = itemView.bar_comparing_model_two
}

