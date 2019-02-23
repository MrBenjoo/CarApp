package com.example.benjo.bil_app_kotlin.ui.comparing.view

import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.otherDimensionsDataOne
import com.example.benjo.bil_app_kotlin.data.otherDimensionsDataTwo
import com.example.benjo.bil_app_kotlin.ui.comparing.BaseCompareView
import com.example.benjo.bil_app_kotlin.ui.comparing.model.CompareData
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.RendererRowProgressbarView
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.RENDERER_TYPE_COMMON

class OtherDimensionsView : BaseCompareView() {

    override fun initListItems() {
        val carOneData = compare.otherDimensionsDataOne()
        val carTwoData = compare.otherDimensionsDataTwo()
        val rendererCommon = RendererRowProgressbarView().also { it.type = RENDERER_TYPE_COMMON }

        listOfItems.add(
                CompareData(
                        RENDERER_TYPE_COMMON,
                        string(R.string.title_compare_length),
                        carOneModel,
                        carOneData?.length,
                        carTwoModel,
                        carTwoData?.length
                )
        )
        listOfItems.add(
                CompareData(
                        RENDERER_TYPE_COMMON,
                        string(R.string.title_compare_width),
                        carOneModel,
                        carOneData?.width,
                        carTwoModel,
                        carTwoData?.width
                )
        )
        listOfItems.add(
                CompareData(
                        RENDERER_TYPE_COMMON,
                        string(R.string.title_compare_height),
                        carOneModel,
                        carOneData?.height,
                        carTwoModel,
                        carTwoData?.height
                )
        )
        listOfItems.add(
                CompareData(
                        RENDERER_TYPE_COMMON,
                        string(R.string.title_compare_axel_width),
                        carOneModel,
                        carOneData?.axelWidth,
                        carTwoModel,
                        carTwoData?.axelWidth
                )
        )

        adapterRenderer.registerRenderer(rendererCommon)

    }

}