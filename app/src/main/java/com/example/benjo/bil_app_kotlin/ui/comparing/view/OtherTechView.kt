package com.example.benjo.bil_app_kotlin.ui.comparing.view

import com.example.benjo.bil_app_kotlin.ui.comparing.BaseCompareView
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.RENDERER_TYPE_COMMON
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.*
import com.example.benjo.bil_app_kotlin.utils.ExplanationHandler
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.RENDERER_TYPE_TECH_OTHER


class OtherTechView : BaseCompareView() {

    override fun initListItems() {
        val carOneData = compare.otherTechDataOne()
        val carTwoData = compare.otherTechDataTwo()
        val rendererUncommon = RendererTechOtherView().also { it.type = RENDERER_TYPE_TECH_OTHER }
        val rendererCommon = RendererRowProgressbarView().also { it.type = RENDERER_TYPE_COMMON }

        with(ExplanationHandler()) {
            listOfItems.add(
                    OtherTechModel(RENDERER_TYPE_TECH_OTHER,
                            string(R.string.title_compare_number_of_passengers),
                            carOneModel,
                            carOneData?.passengers,
                            carTwoModel,
                            carTwoData?.passengers,

                            string(R.string.title_compare_passenger_airbag),
                            carOneModel,
                            boolType(carOneData?.passengerAirbag),
                            carTwoModel,
                            boolType(carTwoData?.passengerAirbag),

                            string(R.string.title_compare_hitch),
                            carOneModel,
                            carOneData?.hitch,
                            carTwoModel,
                            carTwoData?.hitch)
            )
        }

        listOfItems.add(
                CompareData(RENDERER_TYPE_COMMON,
                        string(R.string.title_compare_sound_level),
                        carOneModel,
                        carOneData?.soundLevel,
                        carTwoModel,
                        carTwoData?.soundLevel)
        )

        adapterRenderer.registerRenderer(rendererUncommon)
        adapterRenderer.registerRenderer(rendererCommon)

    }

}