package com.example.benjo.bil_app_kotlin.network.json_parsing

import com.example.benjo.bil_app_kotlin.Constants
import com.example.benjo.bil_app_kotlin.MainActivity
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.list.Row


class JsonMapping(private val activity: MainActivity) {


    fun basicInfoMapping(key: String, value: String?): Row = when (key) {
        Constants.MAKE -> row(R.string.api_make, value)
        Constants.MODEL -> row(R.string.api_model, value)
        Constants.STATUS -> row(R.string.api_status, Explanation().statusType(value))
        Constants.COLOR -> row(R.string.api_color, value)
        Constants.TYPE -> row(R.string.api_type, Explanation().vehType(value))
        Constants.VEH_YEAR -> row(R.string.api_vehicle_year, value)
        Constants.MOD_YEAR -> row(R.string.api_model_year, value)
        else -> Row("", "")
    }

    fun techInfoMapping(key: String, value: String?): Row = when (key) {
        Constants.HP -> row(R.string.api_power_hp_1, value)
        Constants.KW -> row(R.string.api_power_kw_1, "$value kW")
        Constants.CYL_VOL -> row(R.string.api_cylinder_volume, value)
        Constants.TOP_SPEED -> row(R.string.api_top_speed, "$value km/h")
        Constants.FUEL -> row(R.string.api_fuel_1, Explanation().fuelType(value))
        Constants.CONSUMPTION -> row(R.string.api_consumption_1, "$value liter/100km")
        Constants.CO2 -> row(R.string.api_co2_1, "$value g/km")
        Constants.TRANSMISSION -> row(R.string.api_transmission, Explanation().transType(value))
        Constants.FOUR_WHEEL_DRIVE -> row(R.string.api_four_wheel_drive, Explanation().boolType(value))
        Constants.SOUND_LVL -> row(R.string.api_sound_level, "$value dB")
        Constants.NO_PASSENGERS -> row(R.string.api_number_of_passengers, value)
        Constants.PASSENGER_AB -> row(R.string.api_passenger_airbag, Explanation().boolType(value))
        Constants.HITCH -> row(R.string.api_hitch, value)
        Constants.CHASSI_CODE -> row(R.string.api_chassi_code_1, value)
        Constants.CHASSI -> row(R.string.api_chassi, value)
        Constants.LENGTH -> row(R.string.api_length, "$value mm")
        Constants.WIDTH -> row(R.string.api_width, "$value mm")
        Constants.HEIGHT -> row(R.string.api_height, "$value mm")
        Constants.KERB_WEIGHT -> row(R.string.api_kerb_weight, "$value kg")
        Constants.TOTAL_WEIGHT -> row(R.string.api_total_weight, "$value kg")
        Constants.LOAD_WEIGHT -> row(R.string.api_load_weight, "Max $value kg")
        Constants.TRAILER_WEIGHT -> row(R.string.api_trailer_weight, "Max $value kg")
        Constants.UNBR_TRAILER_WEIGHT -> row(R.string.api_unbraked_trailer_weight, "Max $value kg")
        Constants.TRAILER_WEIGHT_B -> row(R.string.api_trailer_weight_b, "Max $value kg")
        Constants.TRAILER_WEIGHT_BE -> row(R.string.api_trailer_weight_be, "Max $value kg")
        Constants.CARR_WEIGHT -> row(R.string.api_carriage_weight, "$value kg")
        Constants.TIRE_FRONT -> row(R.string.api_tire_front, value)
        Constants.TIRE_BACK -> row(R.string.api_tire_back, value)
        Constants.RIM_FRONT -> row(R.string.api_rim_front, value)
        Constants.RIM_BACK -> row(R.string.api_rim_back, value)
        Constants.AXEL_WIDTH -> row(R.string.api_axel_width, value)
        Constants.CATEGORY -> row(R.string.api_category, value)
        Constants.EEG -> row(R.string.api_eeg, value)
        Constants.NOX -> row(R.string.api_nox_1, "$value g/km?")
        Constants.THC_NOX -> row(R.string.api_thc_nox_1, "$value g/km?")
        Constants.ECO_CLASS -> row(R.string.api_eco_class, Explanation().ecoClassType(value))
        Constants.EMISS_CLASS -> row(R.string.api_emission_class, Explanation().emissionType(value))
        Constants.EURO_NCAP -> row(R.string.api_euro_ncap, value)
        else -> Row("", "")
    }

    private fun row(id: Int, value: String?): Row = Row(desc(id), value)
    private fun desc(id: Int): String = activity.resources.getString(id)

}