package com.example.benjo.bil_app_kotlin.ui.comparing.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.ui.comparing.BaseCompareView
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.RENDERER_TYPE_COMMON
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.*
import com.example.benjo.bil_app_kotlin.utils.ExplanationHandler
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.otherTechDataOne
import com.example.benjo.bil_app_kotlin.data.otherTechDataTwo
import com.example.benjo.bil_app_kotlin.ui.comparing.model.CompareData
import com.example.benjo.bil_app_kotlin.ui.comparing.model.OtherTechModel
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.RENDERER_TYPE_TECH_OTHER
import kotlinx.android.synthetic.main.fragment_tech_other.view.*
import kotlinx.android.synthetic.main.view_compare_text_include.view.*


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


class RendererTechOtherView : Renderer<OtherTechModel, TechOtherViewHolder>() {

    override fun createViewHolder(parent: ViewGroup?): TechOtherViewHolder {
        return TechOtherViewHolder(
                LayoutInflater.from(parent?.context).inflate(
                        R.layout.fragment_tech_other,
                        parent,
                        false
                )
        )
    }

    override fun bindView(model: OtherTechModel, holder: TechOtherViewHolder) {
        with(holder) {
            passengerTitle.text = model.passengerTitle
            passengerCarOne.text = model.passengerCarOne ?: "N/A"
            passengerCarOneVal.text = model.passengerCarOneVal ?: "N/A"
            passengerCarTwo.text = model.passengerCarTwo ?: "N/A"
            passengerCarTwoVal.text = model.passengerCarTwoVal ?: "N/A"

            passengerABTitle.text = model.passengerABTitle
            passengerABCarOne.text = model.passengerABCarOne ?: "N/A"
            passengerABCarOneVal.text = model.passengerABCarOneVal ?: "N/A"
            passengerABCarTwo.text = model.passengerABCarTwo ?: "N/A"
            passengerABCarTwoVal.text = model.passengerABCarTwoVal ?: "N/A"

            hitchTitle.text = model.hitchTitle
            hitchCarOne.text = model.hitchCarOne ?: "N/A"
            hitchCarOneVal.text = model.hitchCarOneVal ?: "N/A"
            hitchCarTwo.text = model.hitchCarTwo ?: "N/A"
            hitchCarTwoVal.text = model.hitchCarTwoVal ?: "N/A"
        }
    }
}




class TechOtherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val passengerTitle = itemView.passenger_number.tv_compare_title
    val passengerCarOne = itemView.passenger_number.tv_compare_car_one
    val passengerCarOneVal = itemView.passenger_number.tv_compare_car_one_val
    val passengerCarTwo = itemView.passenger_number.tv_compare_car_two
    val passengerCarTwoVal = itemView.passenger_number.tv_compare_car_two_val

    val passengerABTitle = itemView.passenger_airbag.tv_compare_title
    val passengerABCarOne = itemView.passenger_airbag.tv_compare_car_one
    val passengerABCarOneVal = itemView.passenger_airbag.tv_compare_car_one_val
    val passengerABCarTwo = itemView.passenger_airbag.tv_compare_car_two
    val passengerABCarTwoVal = itemView.passenger_airbag.tv_compare_car_two_val

    val hitchTitle = itemView.hitch.tv_compare_title
    val hitchCarOne = itemView.hitch.tv_compare_car_one
    val hitchCarOneVal = itemView.hitch.tv_compare_car_one_val
    val hitchCarTwo = itemView.hitch.tv_compare_car_two
    val hitchCarTwoVal = itemView.hitch.tv_compare_car_two_val

}