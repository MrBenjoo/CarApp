package com.example.benjo.bil_app_kotlin.ui.comparing.view


import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.weightsDataOne
import com.example.benjo.bil_app_kotlin.data.weightsDataTwo
import com.example.benjo.bil_app_kotlin.ui.comparing.BaseCompareView
import com.example.benjo.bil_app_kotlin.ui.comparing.model.CompareData
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.RendererRowProgressbarView
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.RENDERER_TYPE_COMMON


class WeightsView : BaseCompareView() {

    override fun initListItems() {
        val carOneData = compare.weightsDataOne()
        val carTwoData = compare.weightsDataTwo()
        val rendererCommon = RendererRowProgressbarView().also { it.type = RENDERER_TYPE_COMMON }

        listOfItems.add(
                CompareData(
                        RENDERER_TYPE_COMMON,
                        string(R.string.title_compare_total_weight),
                        carOneModel,
                        carOneData?.totalWeight,
                        carTwoModel,
                        carTwoData?.totalWeight)
        )
        listOfItems.add(
                CompareData(
                        RENDERER_TYPE_COMMON,
                        string(R.string.title_compare_load_weight),
                        carOneModel,
                        carOneData?.loadWeight,
                        carTwoModel,
                        carTwoData?.loadWeight)
        )

        listOfItems.add(
                CompareData(
                        RENDERER_TYPE_COMMON,
                        string(R.string.title_compare_trailer_weight),
                        carOneModel,
                        carOneData?.trailerWeight,
                        carTwoModel,
                        carTwoData?.trailerWeight)
        )

        listOfItems.add(
                CompareData(
                        RENDERER_TYPE_COMMON,
                        string(R.string.title_compare_unbraked_trailer_weight),
                        carOneModel,
                        carOneData?.unbrakedTrailerWeight,
                        carTwoModel,
                        carTwoData?.unbrakedTrailerWeight)
        )

        listOfItems.add(
                CompareData(
                        RENDERER_TYPE_COMMON,
                        string(R.string.title_compare_trailer_weight_b),
                        carOneModel,
                        carOneData?.trailerWeightB,
                        carTwoModel,
                        carTwoData?.trailerWeightB)
        )

        listOfItems.add(
                CompareData(
                        RENDERER_TYPE_COMMON,
                        string(R.string.title_compare_trailer_weight_be),
                        carOneModel,
                        carOneData?.trailerWeightBe,
                        carTwoModel,
                        carTwoData?.trailerWeightBe)
        )
        adapterRenderer.registerRenderer(rendererCommon)
    }


}