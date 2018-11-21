package com.example.benjo.bil_app_kotlin.data.model


import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.utils.MemoryLeaks
import java.lang.NumberFormatException


data class Normalization(val dimension: String, val value: String/*Int*/) {
    private val mapNormalizations = HashMap<String, Float>()

    init {
        with(MemoryLeaks.getContext().resources) {
            mapNormalizations[getString(R.string.title_compare_horsepower)] = 1500F
            mapNormalizations[getString(R.string.title_compare_kilowatt)] = 400F
            mapNormalizations[getString(R.string.title_compare_cylinder_volume)] = 7000F
            mapNormalizations[getString(R.string.title_compare_top_speed)] = 485F


            mapNormalizations[getString(R.string.title_compare_consumption)] = 20F
            // mapNormalizations[getString(R.string.title_compare_co2)] =
            // mapNormalizations[getString(R.string.title_compare_nox)] =
            // mapNormalizations[getString(R.string.title_compare_thc_nox)] =
            mapNormalizations[getString(R.string.title_compare_sound_level)] = 80F

            mapNormalizations[getString(R.string.title_compare_length)] = 5000F
            mapNormalizations[getString(R.string.title_compare_width)] = 2200F
            mapNormalizations[getString(R.string.title_compare_height)] = 4000F


            mapNormalizations[getString(R.string.title_compare_total_weight)] = 3000F
            mapNormalizations[getString(R.string.title_compare_load_weight)] = 700F
            mapNormalizations[getString(R.string.title_compare_trailer_weight)] = 1700F
            mapNormalizations[getString(R.string.title_compare_unbraked_trailer_weight)] = 800F
            mapNormalizations[getString(R.string.title_compare_trailer_weight_b)] = 2000F
            mapNormalizations[getString(R.string.title_compare_trailer_weight_be)] = 3500F
        }
    }

    fun perform(): Int {
        val maxValue = mapNormalizations[dimension]
        var valueInt = 0
        var valueFloat = 0F

        try {
            valueInt = value.toInt()
            return when (maxValue) {
                null -> 0
                else -> ((valueInt / maxValue) * 100).toInt()
            }
        } catch (exception: NumberFormatException) {
            try {
                valueFloat = value.toFloat()
                return when (maxValue) {
                    null -> 0
                    else -> ((valueFloat / maxValue) * 100).toInt()
                }
            } catch (exception: NumberFormatException) {

            }
        }
        return 0
    }

}