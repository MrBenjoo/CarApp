package com.example.benjo.bil_app_kotlin.data.model


import android.util.Log
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.utils.MemoryLeaks


data class Normalization(val dimension : String, val value : Int) {
    private val mapNormalizations = HashMap<String, Float>()

    //MemoryLeaks.getContext().resources.getString(R.string.api_cylinder_volume)
    init {
        with(MemoryLeaks.getContext().resources) {
            mapNormalizations[getString(R.string.title_compare_length)] = 5000F
            mapNormalizations[getString(R.string.title_compare_width)] = 2200F
            mapNormalizations[getString(R.string.title_compare_height)] = 4000F

            mapNormalizations[getString(R.string.title_compare_horsepower)] = 1500F
            //mapNormalizations["kilowatt"] = TODO
            mapNormalizations[getString(R.string.title_compare_cylinder_volume)] = 7000F
            mapNormalizations[getString(R.string.title_compare_top_speed)] = 485F
        }
    }

    fun perfom() : Int {
        val maxValue = mapNormalizations[dimension]
        when(maxValue) {
            null -> return 0
            else ->
            {
                Log.d("Normalization", "perform -> title = $dimension value = $value maxValue = $maxValue")
                val dataReturn = ((value / maxValue) * 100).toInt()
                Log.d("Normalization", "perform -> to return = ${dataReturn.toString()}")
                return dataReturn
            }
        }
    }


}