package com.example.benjo.bil_app_kotlin.ui.comparing.renderer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.RENDERER_TYPE_VEHICLE
import kotlinx.android.synthetic.main.view_compare_row_text_include.view.*

class RendererVehicleView : Renderer<VehicleModel, VehicleViewHolder>() {

    override fun createViewHolder(parent: ViewGroup?): VehicleViewHolder {
        return VehicleViewHolder(
                LayoutInflater.from(parent?.context).inflate(
                        R.layout.view_compare_row_text_include,
                        parent,
                        false
                )
        )
    }

    override fun bindView(model: VehicleModel, holder: VehicleViewHolder) {
        with(holder) {
            title.text = model.title
            carModelOne.text = model.carOneModel
            carOneData.text = model.carOneData
            carModelTwo.text = model.carTwoModel
            carTwoData.text = model.carTwoData
        }
    }
}

data class VehicleModel(
        val TYPE: Int = RENDERER_TYPE_VEHICLE,
        val title: String?,
        val carOneModel: String?,
        val carOneData: String?,
        val carTwoModel: String?,
        val carTwoData: String?
) : ItemModel {
    override fun getType(): Int = TYPE
}


class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title = itemView.tv_compare_row_text_title
    val carModelOne = itemView.tv_compare_row_text_model_one
    val carOneData = itemView.tv_compare_row_text_model_one_val
    val carModelTwo = itemView.tv_compare_row_text_model_two
    val carTwoData = itemView.tv_compare_row_text_model_two_val
}


