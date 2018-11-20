package com.example.benjo.bil_app_kotlin.ui.comparing.renderer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.ui.comparing.constants.Companion.TYPE_ENVIRONMENT
import kotlinx.android.synthetic.main.view_compare_text_include.view.*
import kotlinx.android.synthetic.main.fragment_environment.view.*

class EnvRender : Renderer<EnvModel, EnvViewHolder>() {

    override fun createViewHolder(parent: ViewGroup?): EnvViewHolder {
        return EnvViewHolder(
                LayoutInflater.from(parent?.context).inflate(
                        R.layout.fragment_environment,
                        parent,
                        false
                )
        )
    }

    override fun bindView(model: EnvModel, holder: EnvViewHolder) {
        with(holder) {
            fuelTitle.text = model.fuelTitle ?: "N/A"
            fuelCarOne.text = model.fuelCarOne ?: "N/A"
            fuelCarOneVal.text = model.fuelCarOneVal ?: "N/A"
            fuelCarTwo.text = model.fuelCarTwo ?: "N/A"
            fuelCarTwoVal.text = model.fuelCarTwoVal ?: "N/A"

            ecoTitle.text = model.ecoTitle ?: "N/A"
            ecoCarOne.text = model.ecoCarOne ?: "N/A"
            ecoCarOneVal.text = model.ecoCarOneVal ?: "N/A"
            ecoCarTwo.text = model.ecoCarTwo ?: "N/A"
            ecoCarTwoVal.text = model.ecoCarTwoVal ?: "N/A"

            fourTitle.text = model.fourTitle ?: "N/A"
            fourCarOne.text = model.fourCarOne ?: "N/A"
            fourCarOneVal.text = model.fourCarOneVal ?: "N/A"
            fourCarTwo.text = model.fourCarTwo ?: "N/A"
            fourCarTwoVal.text = model.fourCarTwoVal ?: "N/A"

            transmissionTitle.text = model.transmissionTitle ?: "N/A"
            transmissionCarOne.text = model.transmissionCarOne ?: "N/A"
            transmissionCarOneVal.text = model.transmissionCarOneVal ?: "N/A"
            transmissionCarTwo.text = model.transmissionCarTwo ?: "N/A"
            transmissionCarTwoVal.text = model.transmissionCarTwoVal ?: "N/A"
        }
    }
}

data class EnvModel(
        val TYPE: Int = TYPE_ENVIRONMENT,
        val fuelTitle: String,
        val fuelCarOne: String?,
        val fuelCarOneVal: String?,
        val fuelCarTwo: String?,
        val fuelCarTwoVal: String?,
        val ecoTitle: String,
        val ecoCarOne: String?,
        val ecoCarOneVal: String?,
        val ecoCarTwo: String?,
        val ecoCarTwoVal: String?,
        val fourTitle: String,
        val fourCarOne: String?,
        val fourCarOneVal: String?,
        val fourCarTwo: String?,
        val fourCarTwoVal: String?,
        val transmissionTitle: String,
        val transmissionCarOne: String?,
        val transmissionCarOneVal: String?,
        val transmissionCarTwo: String?,
        val transmissionCarTwoVal: String?
) : ItemModel {
    override fun getType(): Int = TYPE
}


class EnvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val fuelTitle = itemView.fuel.tv_compare_title
    val fuelCarOne = itemView.fuel.tv_compare_car_one
    val fuelCarOneVal = itemView.fuel.tv_compare_car_one_val
    val fuelCarTwo = itemView.fuel.tv_compare_car_two
    val fuelCarTwoVal = itemView.fuel.tv_compare_car_two_val

    val ecoTitle = itemView.ecoclass.tv_compare_title
    val ecoCarOne = itemView.ecoclass.tv_compare_car_one
    val ecoCarOneVal = itemView.ecoclass.tv_compare_car_one_val
    val ecoCarTwo = itemView.ecoclass.tv_compare_car_two
    val ecoCarTwoVal = itemView.ecoclass.tv_compare_car_two_val

    val fourTitle = itemView.fourwheeldrive.tv_compare_title
    val fourCarOne = itemView.fourwheeldrive.tv_compare_car_one
    val fourCarOneVal = itemView.fourwheeldrive.tv_compare_car_one_val
    val fourCarTwo = itemView.fourwheeldrive.tv_compare_car_two
    val fourCarTwoVal = itemView.fourwheeldrive.tv_compare_car_two_val

    val transmissionTitle = itemView.transmission.tv_compare_title
    val transmissionCarOne = itemView.transmission.tv_compare_car_one
    val transmissionCarOneVal = itemView.transmission.tv_compare_car_one_val
    val transmissionCarTwo = itemView.transmission.tv_compare_car_two
    val transmissionCarTwoVal = itemView.transmission.tv_compare_car_two_val
}


