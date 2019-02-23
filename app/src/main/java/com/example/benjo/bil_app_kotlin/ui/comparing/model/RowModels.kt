package com.example.benjo.bil_app_kotlin.ui.comparing.model

import com.example.benjo.bil_app_kotlin.utils.Constants

data class EnvModel(
        val TYPE: Int = Constants.RENDERER_TYPE_ENVIRONMENT,
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


data class CompareData(
        val TYPE: Int = Constants.RENDERER_TYPE_COMMON,
        val title: String,
        val carOneModel: String?,
        val carOneData: String?,
        val carTwoModel: String?,
        val carTwoData: String?
) : ItemModel {

    override fun getType(): Int = TYPE
}


data class VehicleModel(
        val TYPE: Int = Constants.RENDERER_TYPE_VEHICLE,
        val title: String?,
        val carOneModel: String?,
        val carOneData: String?,
        val carTwoModel: String?,
        val carTwoData: String?
) : ItemModel {
    override fun getType(): Int = TYPE
}


data class OtherTechModel(
        val TYPE: Int = Constants.RENDERER_TYPE_TECH_OTHER,
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

interface ItemModel {

    fun getType(): Int

}