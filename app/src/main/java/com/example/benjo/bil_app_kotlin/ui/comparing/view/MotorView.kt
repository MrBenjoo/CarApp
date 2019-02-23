package com.example.benjo.bil_app_kotlin.ui.comparing.view


import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.RENDERER_TYPE_COMMON
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.motorDataOne
import com.example.benjo.bil_app_kotlin.data.motorDataTwo
import com.example.benjo.bil_app_kotlin.ui.comparing.BaseCompareView
import com.example.benjo.bil_app_kotlin.ui.comparing.model.CompareData
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.RendererRowProgressbarView


class MotorView : BaseCompareView() {

    override fun initListItems() {
        val carOneData = compare.motorDataOne()
        val carTwoData = compare.motorDataTwo()
        val renderer = RendererRowProgressbarView().also { it.type = RENDERER_TYPE_COMMON }

        listOfItems.add(
                CompareData(
                        RENDERER_TYPE_COMMON,
                        string(R.string.title_compare_horsepower),
                        carOneModel,
                        carOneData?.horsepower,
                        carTwoModel,
                        carTwoData?.horsepower
                )
        )
        listOfItems.add(
                CompareData(
                        RENDERER_TYPE_COMMON,
                        string(R.string.title_compare_kilowatt),
                        carOneModel,
                        carOneData?.kilowatt,
                        carTwoModel,
                        carTwoData?.kilowatt
                )
        )
        listOfItems.add(
                CompareData(
                        RENDERER_TYPE_COMMON,
                        string(R.string.title_compare_cylinder_volume),
                        carOneModel,
                        carOneData?.volume,
                        carTwoModel,
                        carTwoData?.volume
                )
        )
        listOfItems.add(
                CompareData(
                        RENDERER_TYPE_COMMON,
                        string(R.string.title_compare_top_speed),
                        carOneModel,
                        carOneData?.topSpeed,
                        carTwoModel,
                        carTwoData?.topSpeed
                )
        )
        adapterRenderer.registerRenderer(renderer)
    }

}
