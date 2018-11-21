package com.example.benjo.bil_app_kotlin.ui.comparing.view

import com.example.benjo.bil_app_kotlin.ui.comparing.BaseCompareView
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.VehicleModel
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.RendererVehicleView
import com.example.benjo.bil_app_kotlin.utils.ExplanationHandler
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.RENDERER_TYPE_VEHICLE

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
