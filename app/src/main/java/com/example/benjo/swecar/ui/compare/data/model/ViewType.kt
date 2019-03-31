package com.example.benjo.swecar.ui.compare.data.model

import com.example.benjo.swecar.ui.compare.renderer.Renderer

interface ItemCompareModel {
    fun getType(): Int
}

data class EnvironmentCompareModel(
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
) : ItemCompareModel {
    override fun getType(): Int = Renderer.Type.ENVIRONMENT
}


data class CommonCompareModel(
        val title: String,
        val carOneModel: String?,
        val carOneData: String?,
        val carTwoModel: String?,
        val carTwoData: String?
) : ItemCompareModel {
    override fun getType(): Int = Renderer.Type.COMMON
}


data class VehicleCompareModel(
        val title: String?,
        val carOneModel: String?,
        val carOneData: String?,
        val carTwoModel: String?,
        val carTwoData: String?
) : ItemCompareModel {
    override fun getType(): Int = Renderer.Type.VEHICLE
}


data class OtherTechCompareModel(
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
) : ItemCompareModel {
    override fun getType(): Int = Renderer.Type.TECH_OTHER
}

