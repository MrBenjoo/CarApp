package com.example.benjo.bil_app_kotlin.network.json_parsing

import com.example.benjo.bil_app_kotlin.MainActivity
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.list.Row


class JsonMapping(private val activity: MainActivity) {

    fun basicInfoMapping(key: String, value: String?): Row = when (key) {
        "make" -> row(R.string.api_make, value)
        "model" -> row(R.string.api_model, value)
        "status" -> row(R.string.api_status, Explanation().statusType(value))
        "color" -> row(R.string.api_color, value)
        "type" -> row(R.string.api_type, Explanation().vehType(value))
        "vehicle_year" -> row(R.string.api_vehicle_year, value)
        "model_year" -> row(R.string.api_model_year, value)
        else -> Row("", "")
    }

    fun techInfoMapping(key: String, value: String?): Row = when (key) {
        "power_hp_1" -> row(R.string.api_power_hp_1, value)
        "power_kw_1" -> row(R.string.api_power_kw_1, "$value kW")
        "cylinder_volume" -> row(R.string.api_cylinder_volume, value)
        "top_speed" -> row(R.string.api_top_speed, "$value km/h")
        "fuel_1" -> row(R.string.api_fuel_1, Explanation().fuelType(value))
        "consumption_1" -> row(R.string.api_consumption_1, "$value liter/100km")
        "co2_1" -> row(R.string.api_co2_1, "$value g/km")
        "transmission" -> row(R.string.api_transmission, Explanation().transType(value))
        "four_wheel_drive" -> row(R.string.api_four_wheel_drive, Explanation().boolType(value))
        "sound_level_1" -> row(R.string.api_sound_level, "$value dB")
        "number_of_passengers" -> row(R.string.api_number_of_passengers, value)
        "passenger_airbag" -> row(R.string.api_passenger_airbag, Explanation().boolType(value))
        "hitch" -> row(R.string.api_hitch, value)
        "chassi_code_1" -> row(R.string.api_chassi_code_1, value)
        "chassi" -> row(R.string.api_chassi, value)
        "length" -> row(R.string.api_length, "$value mm")
        "width" -> row(R.string.api_width, "$value mm")
        "height" -> row(R.string.api_height, "$value mm")
        "kerb_weight" -> row(R.string.api_kerb_weight, "$value kg")
        "total_weight" -> row(R.string.api_total_weight, "$value kg")
        "load_weight" -> row(R.string.api_load_weight, "Max $value kg")
        "trailer_weight" -> row(R.string.api_trailer_weight, "Max $value kg")
        "unbraked_trailer_weight" -> row(R.string.api_unbraked_trailer_weight, "Max $value kg")
        "trailer_weight_b" -> row(R.string.api_trailer_weight_b, "Max $value kg")
        "trailer_weight_be" -> row(R.string.api_trailer_weight_be, "Max $value kg")
        "carriage_weight" -> row(R.string.api_carriage_weight, "$value kg")
        "tire_front" -> row(R.string.api_tire_front, value)
        "tire_back" -> row(R.string.api_tire_back, value)
        "rim_front" -> row(R.string.api_rim_front, value)
        "rim_back" -> row(R.string.api_rim_back, value)
        "axel_width" -> row(R.string.api_axel_width, value)
        "category" -> row(R.string.api_category, value)
        "eeg" -> row(R.string.api_eeg, value)
        "nox_1" -> row(R.string.api_nox_1, "$value g/km?")
        "thc_nox_1" -> row(R.string.api_thc_nox_1, "$value g/km?")
        "eco_class" -> row(R.string.api_eco_class, Explanation().ecoClassType(value))
        "emission_class" -> row(R.string.api_emission_class, Explanation().emissionType(value))
        "euro_ncap" -> row(R.string.api_euro_ncap, value)
        else -> Row("", "")
    }

    private fun row(id: Int, value: String?): Row = Row(desc(id), value)
    private fun desc(id: Int): String = activity.resources.getString(id)

}