package com.example.benjo.bil_app_kotlin.ui.comparing.renderer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.RENDERER_TYPE_TECH_OTHER
import kotlinx.android.synthetic.main.fragment_tech_other.view.*
import kotlinx.android.synthetic.main.view_compare_text_include.view.*


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

    override fun bindView(techModel: OtherTechModel, holder: TechOtherViewHolder) {
        with(holder) {
            passengerTitle.text = techModel.passengerTitle
            passengerCarOne.text = techModel.passengerCarOne ?: "N/A"
            passengerCarOneVal.text = techModel.passengerCarOneVal ?: "N/A"
            passengerCarTwo.text = techModel.passengerCarTwo ?: "N/A"
            passengerCarTwoVal.text = techModel.passengerCarTwoVal ?: "N/A"

            passengerABTitle.text = techModel.passengerABTitle
            passengerABCarOne.text = techModel.passengerABCarOne ?: "N/A"
            passengerABCarOneVal.text = techModel.passengerABCarOneVal ?: "N/A"
            passengerABCarTwo.text = techModel.passengerABCarTwo ?: "N/A"
            passengerABCarTwoVal.text = techModel.passengerABCarTwoVal ?: "N/A"

            hitchTitle.text = techModel.hitchTitle
            hitchCarOne.text = techModel.hitchCarOne ?: "N/A"
            hitchCarOneVal.text = techModel.hitchCarOneVal ?: "N/A"
            hitchCarTwo.text = techModel.hitchCarTwo ?: "N/A"
            hitchCarTwoVal.text = techModel.hitchCarTwoVal ?: "N/A"
        }
    }
}

data class OtherTechModel(
        val TYPE: Int = RENDERER_TYPE_TECH_OTHER,
        val passengerTitle: String,
        val passengerCarOne: String?,
        val passengerCarOneVal: String?,
        val passengerCarTwo: String?,
        val passengerCarTwoVal: String?,
        val passengerABTitle: String,
        val passengerABCarOne: String?,
        val passengerABCarOneVal: String?,
        val passengerABCarTwo: String?,
        val passengerABCarTwoVal: String?,
        val hitchTitle: String,
        val hitchCarOne: String?,
        val hitchCarOneVal: String?,
        val hitchCarTwo: String?,
        val hitchCarTwoVal: String?
) : ItemModel {
    override fun getType(): Int = TYPE
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