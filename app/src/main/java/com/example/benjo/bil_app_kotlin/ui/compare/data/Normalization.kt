package com.example.benjo.bil_app_kotlin.ui.compare.data


import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.App


data class Normalization(val dimension: String, val value: String) {
    private val mapMaxValues = HashMap<String, Float>()

    init {
        with(App.getContext().resources) {
            mapMaxValues[getString(R.string.title_compare_horsepower)] = 1500F
            mapMaxValues[getString(R.string.title_compare_kilowatt)] = 400F
            mapMaxValues[getString(R.string.title_compare_cylinder_volume)] = 7000F
            mapMaxValues[getString(R.string.title_compare_top_speed)] = 485F


            mapMaxValues[getString(R.string.title_compare_consumption)] = 20F
            // mapMaxValues[getString(R.string.title_compare_co2)] =
            // mapMaxValues[getString(R.string.title_compare_nox)] =
            // mapMaxValues[getString(R.string.title_compare_thc_nox)] =
            mapMaxValues[getString(R.string.title_compare_sound_level)] = 80F

            mapMaxValues[getString(R.string.title_compare_length)] = 5000F
            mapMaxValues[getString(R.string.title_compare_width)] = 2200F
            mapMaxValues[getString(R.string.title_compare_height)] = 4000F


            mapMaxValues[getString(R.string.title_compare_total_weight)] = 3000F
            mapMaxValues[getString(R.string.title_compare_load_weight)] = 700F
            mapMaxValues[getString(R.string.title_compare_trailer_weight)] = 1700F
            mapMaxValues[getString(R.string.title_compare_unbraked_trailer_weight)] = 800F
            mapMaxValues[getString(R.string.title_compare_trailer_weight_b)] = 2000F
            mapMaxValues[getString(R.string.title_compare_trailer_weight_be)] = 3500F
        }
    }

    fun getMaxValue(dimension : String) : Float? = mapMaxValues[dimension]

}