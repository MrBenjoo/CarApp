package com.example.benjo.bil_app_kotlin.ui.comparing.view


import com.example.benjo.bil_app_kotlin.ui.comparing.BaseCompareView
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.EnvModel
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.RendererEnvironmentView
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.RendererRowProgressbarView
import com.example.benjo.bil_app_kotlin.utils.ExplanationHandler
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.CompareData
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.RENDERER_TYPE_COMMON
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.RENDERER_TYPE_ENVIRONMENT


class EnvironmentView : BaseCompareView() {

    override fun initListItems() {
        val carOneData = compare.environmentDataOne()
        val carTwoData = compare.environmentDataTwo()
        val rendererEnvironment = RendererEnvironmentView().also { it.type = RENDERER_TYPE_ENVIRONMENT }
        val rendererCommon = RendererRowProgressbarView().also { it.type = RENDERER_TYPE_COMMON }

        with(ExplanationHandler()) {
            listOfItems.add(
                    EnvModel(RENDERER_TYPE_ENVIRONMENT,
                            string(R.string.title_compare_fuel),
                            carOneModel,
                            fuelType(carOneData?.fuel),
                            carTwoModel,
                            fuelType(carTwoData?.fuel),

                            string(R.string.title_compare_eco_class),
                            carOneModel,
                            ecoClassType(carOneData?.ecoClass),
                            carTwoModel,
                            ecoClassType(carTwoData?.ecoClass),

                            string(R.string.title_compare_four_wheel_drive),
                            carOneModel,
                            boolType(carOneData?.fourWheelDrive),
                            carTwoModel,
                            boolType(carTwoData?.fourWheelDrive),

                            string(R.string.title_compare_transmission),
                            carOneModel,
                            transType(carOneData?.transmission),
                            carTwoModel,
                            transType(carTwoData?.transmission)
                    ))

            listOfItems.add(
                    CompareData(RENDERER_TYPE_COMMON,
                            string(R.string.title_compare_consumption),
                            carOneModel,
                            carOneData?.consumption,
                            carTwoModel,
                            carTwoData?.consumption)
            )

            listOfItems.add(
                    CompareData(RENDERER_TYPE_COMMON,
                            string(R.string.title_compare_co2),
                            carOneModel,
                            carOneData?.co2,
                            carTwoModel,
                            carTwoData?.co2)
            )

            listOfItems.add(
                    CompareData(RENDERER_TYPE_COMMON,
                            string(R.string.title_compare_nox),
                            carOneModel,
                            carOneData?.nox,
                            carTwoModel,
                            carTwoData?.nox)
            )

            listOfItems.add(
                    CompareData(RENDERER_TYPE_COMMON,
                            string(R.string.title_compare_thc_nox),
                            carOneModel,
                            carOneData?.thcNox,
                            carTwoModel,
                            carTwoData?.thcNox)
            )
        }
        adapterRenderer.registerRenderer(rendererEnvironment)
        adapterRenderer.registerRenderer(rendererCommon)
    }

}
