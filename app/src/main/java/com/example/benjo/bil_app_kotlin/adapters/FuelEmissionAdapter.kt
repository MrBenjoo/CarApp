package com.example.benjo.bil_app_kotlin.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.model.ComparableData
import com.example.benjo.bil_app_kotlin.data.model.FuelEcoEmission
import com.example.benjo.bil_app_kotlin.ui.comparing.CommonViewHolder
import com.example.benjo.bil_app_kotlin.ui.comparing.FuelEmissionViewHolder

class FuelEmissionAdapter(val listOfComparableData: ArrayList<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        0 -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_row_fuel_emission, parent, false)
            FuelEmissionViewHolder(view)
        }
        else -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_row_common, parent, false)
            CommonViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when (holder.itemViewType) {
        0 -> onBindFuelEmissionHolder(holder as FuelEmissionViewHolder, position)
        else -> onBindCommonViewHolder(holder as CommonViewHolder, position)
    }

    private fun onBindFuelEmissionHolder(fuelEmissionHolder: FuelEmissionViewHolder, position: Int) {
        val fuelEcoEmission = listOfComparableData[position] as FuelEcoEmission

        fuelEmissionHolder.fuelTitle.text = fuelEcoEmission.fuelTitle
        fuelEmissionHolder.fuelCarModelOne.text = fuelEcoEmission.fuelCarModelOne ?: "N/A"
        fuelEmissionHolder.fuelCarOneData.text = fuelEcoEmission.fuelCarOneData ?: "N/A"
        fuelEmissionHolder.fuelCarModelTwo.text = fuelEcoEmission.fuelCarModelTwo ?: "N/A"
        fuelEmissionHolder.fuelCarTwoData.text = fuelEcoEmission.fuelCarTwoData ?: "N/A"

        fuelEmissionHolder.ecoTitle.text = fuelEcoEmission.ecoTitle
        fuelEmissionHolder.ecoCarModelOne.text = fuelEcoEmission.ecoCarModelOne ?: "N/A"
        fuelEmissionHolder.ecoCarOneData.text = fuelEcoEmission.ecoCarOneData ?: "N/A"
        fuelEmissionHolder.ecoCarModelTwo.text = fuelEcoEmission.ecoCarModelTwo ?: "N/A"
        fuelEmissionHolder.ecoCarTwoData.text = fuelEcoEmission.ecoCarTwoData ?: "N/A"

        fuelEmissionHolder.emissionTitle.text = fuelEcoEmission.emissionTitle
        fuelEmissionHolder.emissionCarModelOne.text = fuelEcoEmission.emissionCarModelOne ?: "N/A"
        fuelEmissionHolder.emissionCarOneData.text = fuelEcoEmission.emissionCarOneData ?: "N/A"
        fuelEmissionHolder.emissionCarModelTwo.text = fuelEcoEmission.emissionCarModelTwo ?: "N/A"
        fuelEmissionHolder.emissionCarTwoData.text = fuelEcoEmission.emissionCarTwoData ?: "N/A"
    }

    private fun onBindCommonViewHolder(commonViewHolder: CommonViewHolder, position: Int) {
        val other = listOfComparableData[position] as ComparableData
        commonViewHolder.title.text = other.title
        commonViewHolder.carModelOne.text = other.carModelOne ?: "N/A"
        commonViewHolder.carOneData.text = other.carOneData ?: "N/A"
        commonViewHolder.carModelTwo.text = other.carModelTwo ?: "N/A"
        commonViewHolder.carTwoData.text = other.carTwoData ?: "N/A"
    }

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = listOfComparableData.size

}
