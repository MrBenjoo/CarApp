package com.example.benjo.bil_app_kotlin.network.json_parsing

import com.google.gson.annotations.SerializedName


data class Result(@SerializedName("data") val carInfo: CarInfo?)

data class CarInfo(val basic: Basic?, val technical: Technical?)

data class Basic(val data: BasicInfo?)

data class Technical(val data: TechnicalData?)

data class BasicInfo(
        val make: String?,
        val model: String?,
        val status: String?,
        val color: String?,
        val type: String?,
        val vehicle_year: String?,
        val model_year: String?
)

data class TechnicalData(
        val power_hp_1: String?,
        val power_kw_1: String?,
        val cylinder_volume: String?,
        val top_speed: String?,
        val fuel_1: String?,
        val consumption_1: String?,
        val co2_1: String?,
        val transmission: String?,
        val four_wheel_drive: String?,
        val sound_level_1: String?,
        val number_of_passengers: String?,
        val passenger_airbag: String?,
        val hitch: String?,
        val chassi_code_1: String?,
        val chassi: String?,
        val length: String?,
        val width: String?,
        val height: String?,
        val kerb_weight: String?,
        val total_weight: String?,
        val load_weight: String?,
        val trailer_weight: String?,
        val unbraked_trailer_weight: String?,
        val trailer_weight_b: String?,
        val trailer_weight_be: String?,
        val carriage_weight: String?,
        val tire_front: String?,
        val tire_back: String?,
        val rim_front: String?,
        val rim_back: String?,
        val axel_width: String?,
        val category: String?,
        val eeg: String?,
        val nox_1: String?,
        val thc_nox_1: String?,
        val eco_class: String?,
        val emission_class: String?,
        val euro_ncap: String?
)
