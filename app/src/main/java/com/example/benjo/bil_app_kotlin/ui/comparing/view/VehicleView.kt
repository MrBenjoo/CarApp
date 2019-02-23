package com.example.benjo.bil_app_kotlin.ui.comparing.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.ui.comparing.BaseCompareView
import com.example.benjo.bil_app_kotlin.utils.ExplanationHandler
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.vehicleDataOne
import com.example.benjo.bil_app_kotlin.data.vehicleDataTwo
import com.example.benjo.bil_app_kotlin.ui.comparing.model.VehicleModel
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.Renderer
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.RENDERER_TYPE_VEHICLE
import kotlinx.android.synthetic.main.view_compare_row_text_include.view.*

class VehicleView : BaseCompareView() {

    override fun initListItems() {
        val carOneData = compare.vehicleDataOne()
        val carTwoData = compare.vehicleDataTwo()
        val renderer = RendererVehicleView().also { it.type = RENDERER_TYPE_VEHICLE }

        with(ExplanationHandler()) {
            listOfItems.add(
                    VehicleModel(
                            RENDERER_TYPE_VEHICLE,
                            string(R.string.title_compare_model_year),
                            carOneModel,
                            carOneData?.modelYear,
                            carTwoModel,
                            carTwoData?.modelYear
                    )
            )
            listOfItems.add(
                    VehicleModel(
                            RENDERER_TYPE_VEHICLE,
                            string(R.string.title_compare_vehicle_year),
                            carOneModel,
                            carOneData?.vehicleYear,
                            carTwoModel,
                            carTwoData?.vehicleYear
                    )
            )
            listOfItems.add(
                    VehicleModel(
                            RENDERER_TYPE_VEHICLE,
                            string(R.string.title_compare_vehicle_type),
                            carOneModel,
                            carOneData?.type,
                            carTwoModel,
                            carTwoData?.type
                    )
            )
            listOfItems.add(
                    VehicleModel(
                            RENDERER_TYPE_VEHICLE,
                            string(R.string.title_compare_reg),
                            carOneModel,
                            carOneData?.reg,
                            carTwoModel,
                            carTwoData?.reg
                    )
            )

            listOfItems.add(
                    VehicleModel(
                            RENDERER_TYPE_VEHICLE,
                            string(R.string.title_compare_status),
                            carOneModel,
                            statusType(carOneData?.status),
                            carTwoModel,
                            statusType(carTwoData?.status)
                    )
            )

            listOfItems.add(
                    VehicleModel(
                            RENDERER_TYPE_VEHICLE,
                            string(R.string.title_compare_color),
                            carOneModel,
                            carOneData?.color,
                            carTwoModel,
                            carTwoData?.color
                    )
            )

            listOfItems.add(
                    VehicleModel(
                            RENDERER_TYPE_VEHICLE,
                            string(R.string.title_compare_chassi),
                            carOneModel,
                            carOneData?.chassi,
                            carTwoModel,
                            carTwoData?.chassi
                    )
            )

            listOfItems.add(
                    VehicleModel(
                            RENDERER_TYPE_VEHICLE,
                            string(R.string.title_compare_category),
                            carOneModel,
                            carOneData?.category,
                            carTwoModel,
                            carTwoData?.category
                    )
            )
            listOfItems.add(
                    VehicleModel(
                            RENDERER_TYPE_VEHICLE,
                            string(R.string.title_compare_eeg),
                            carOneModel,
                            carOneData?.eeg,
                            carTwoModel,
                            carTwoData?.eeg
                    )
            )
        }
        adapterRenderer.registerRenderer(renderer)
    }

}

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




class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title = itemView.tv_compare_row_text_title
    val carModelOne = itemView.tv_compare_row_text_model_one
    val carOneData = itemView.tv_compare_row_text_model_one_val
    val carModelTwo = itemView.tv_compare_row_text_model_two
    val carTwoData = itemView.tv_compare_row_text_model_two_val
}
