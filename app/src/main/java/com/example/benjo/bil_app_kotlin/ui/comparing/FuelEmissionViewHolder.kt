package com.example.benjo.bil_app_kotlin.ui.comparing

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.view_row_fuel_emission.view.*

class FuelEmissionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val fuelTitle = itemView.tv_fuel_emission_fuel
    val fuelCarModelOne = itemView.tv_fuel_car_one
    val fuelCarOneData = itemView.tv_fuel_car_one_data
    val fuelCarModelTwo = itemView.tv_fuel_car_two
    val fuelCarTwoData = itemView.tv_fuel_car_two_data

    val ecoTitle = itemView.tv_ecoclass_title
    val ecoCarModelOne = itemView.tv_ecoclass_car_one
    val ecoCarOneData = itemView.tv_ecoclass_car_one_data
    val ecoCarModelTwo = itemView.tv_ecoclass_car_two
    val ecoCarTwoData = itemView.tv_ecoclass_car_two_data

    val emissionTitle = itemView.tv_emission_title
    val emissionCarModelOne = itemView.tv_emission_car_one
    val emissionCarOneData = itemView.tv_emission_car_one_data
    val emissionCarModelTwo = itemView.tv_emission_car_two
    val emissionCarTwoData = itemView.tv_emission_car_two_data

}