package com.example.benjo.bil_app_kotlin.domain

import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.ui.tab.Row
import com.example.benjo.bil_app_kotlin.utils.Constants
import com.example.benjo.bil_app_kotlin.utils.ExplanationHandler
import com.example.benjo.bil_app_kotlin.utils.MemoryLeaks

class JsonHandler {

    fun technicalSectionList(map: HashMap<String, String?>): ArrayList<Row> {
        val newMap = HashMap<String, String?>()
        with(Constants) {
            with(ExplanationHandler()) {
                newMap[HP_1] = text(map[HP_1], map[HP_2], map[HP_3])
                newMap[KW_1] = text(map[KW_1], map[KW_2], map[KW_3])
                newMap[FUEL_1] = text(fuelType(map[FUEL_1]), fuelType(map[FUEL_2]))
                newMap[CONSUMPTION_1] = text(map[CONSUMPTION_1], map[CONSUMPTION_2], map[CONSUMPTION_3])
                newMap[CO2_1] = text(map[CO2_1], map[CO2_2], map[CO2_3])
                newMap[SOUND_LVL_1] = text(map[SOUND_LVL_1], map[SOUND_LVL_2], map[SOUND_LVL_3])
                newMap[HITCH] = text(map[HITCH], map[HITCH_2])
                newMap[CYL_VOL] = map[CYL_VOL]
                newMap[TOP_SPEED] = map[TOP_SPEED]
                newMap[TRANSMISSION] = map[TRANSMISSION]
                newMap[FOUR_WHEEL_DRIVE] = map[FOUR_WHEEL_DRIVE]
                newMap[NO_PASSENGERS] = map[NO_PASSENGERS]
                newMap[PASSENGER_AB] = map[PASSENGER_AB]
            }
        }
        return removeEmptyValues(newMap)
    }

    fun dimensionsSectionList(map: HashMap<String, String?>): ArrayList<Row> {
        val newMap = HashMap<String, String?>()
        with(Constants) {
            newMap[CHASSI_CODE_1] = map[CHASSI_CODE_1]
            newMap[CHASSI] = map[CHASSI]
            newMap[LENGTH] = map[LENGTH]
            newMap[WIDTH] = map[WIDTH]
            newMap[HEIGHT] = map[HEIGHT]
            newMap[CARR_WEIGHT] = map[CARR_WEIGHT]
            newMap[TOTAL_WEIGHT] = map[TOTAL_WEIGHT]
            newMap[TRAILER_WEIGHT] = map[TRAILER_WEIGHT]
            newMap[UNBR_TRAILER_WEIGHT] = map[UNBR_TRAILER_WEIGHT]
            newMap[TRAILER_WEIGHT_B] = map[TRAILER_WEIGHT_B]
            newMap[TRAILER_WEIGHT_BE] = map[TRAILER_WEIGHT_BE]
            newMap[TIRE_FRONT] = map[TIRE_FRONT]
            newMap[TIRE_BACK] = map[TIRE_BACK]
            newMap[RIM_FRONT] = map[RIM_FRONT]
            newMap[RIM_BACK] = map[RIM_BACK]
        }
        return removeEmptyValues(newMap)
    }

    fun otherSectionList(map: HashMap<String, String?>): ArrayList<Row> {
        val newMap = HashMap<String, String?>()
        with(Constants) {
            newMap[CATEGORY] = map[CATEGORY]
            newMap[EEG] = map[EEG]
            newMap[NOX_1] = text(map[NOX_1], map[NOX_2], map[NOX_3])
            newMap[THC_NOX_1] = text(map[THC_NOX_1], map[THC_NOX_2], map[THC_NOX_3])
        }
        return removeEmptyValues(newMap)
    }


    private fun text(value_1: String?, value_2: String?, value_3: String? = ""): String? {
        var formattedString = value_1
        formattedString = concatenation(value_2, formattedString)
        formattedString = concatenation(value_3, formattedString)
        return formattedString
    }

    private fun concatenation(value: String?, formattedString: String?): String? {
        return when (!value.isNullOrBlank()) {
            true -> {
                if (!formattedString.isNullOrBlank()) "$formattedString / $value"
                else value
            }
            false -> formattedString
        }
    }

    fun removeEmptyValues(map: Map<String, String?>): ArrayList<Row> {
        val result = ArrayList<Row>()
        for ((key, value) in map)
            if (!value.isNullOrEmpty()) result.add(row(key, value))
        return result
    }

    private fun row(description: String, data: String?): Row = when (description) {
        /* Basic section */
        Constants.MAKE -> row(R.string.api_make, data)
        Constants.MODEL -> row(R.string.api_model, data)
        Constants.STATUS -> row(R.string.api_status, ExplanationHandler().statusType(data))
        Constants.COLOR -> row(R.string.api_color, data)
        Constants.TYPE -> row(R.string.api_type, ExplanationHandler().vehType(data))
        Constants.VEH_YEAR -> row(R.string.api_vehicle_year, data)
        Constants.MOD_YEAR -> row(R.string.api_model_year, data)

        /* Technical section */
        Constants.HP_1 -> row(R.string.api_power_hp_1, data)
        Constants.KW_1 -> row(R.string.api_power_kw_1, "$data kW")
        Constants.CYL_VOL -> row(R.string.api_cylinder_volume, data)
        Constants.TOP_SPEED -> row(R.string.api_top_speed, "$data km/h")
        Constants.FUEL_1 -> row(R.string.api_fuel_1, data)
        Constants.CONSUMPTION_1 -> row(R.string.api_consumption_1, "$data liter/100km")
        Constants.CO2_1 -> row(R.string.api_co2_1, "$data g/km")
        Constants.TRANSMISSION -> row(R.string.api_transmission, ExplanationHandler().transType(data))
        Constants.FOUR_WHEEL_DRIVE -> row(R.string.api_four_wheel_drive, ExplanationHandler().boolType(data))
        Constants.SOUND_LVL_1 -> row(R.string.api_sound_level, "$data dB")
        Constants.NO_PASSENGERS -> row(R.string.api_number_of_passengers, data)
        Constants.PASSENGER_AB -> row(R.string.api_passenger_airbag, ExplanationHandler().boolType(data))
        Constants.HITCH -> row(R.string.api_hitch, data)
        Constants.CHASSI_CODE_1 -> row(R.string.api_chassi_code_1, data)
        Constants.CHASSI -> row(R.string.api_chassi, data)
        Constants.LENGTH -> row(R.string.api_length, "$data mm")
        Constants.WIDTH -> row(R.string.api_width, "$data mm")
        Constants.HEIGHT -> row(R.string.api_height, "$data mm")
        Constants.KERB_WEIGHT -> row(R.string.api_kerb_weight, "$data kg")
        Constants.TOTAL_WEIGHT -> row(R.string.api_total_weight, "$data kg")
        Constants.LOAD_WEIGHT -> row(R.string.api_load_weight, "Max $data kg")
        Constants.TRAILER_WEIGHT -> row(R.string.api_trailer_weight, "Max $data kg")
        Constants.UNBR_TRAILER_WEIGHT -> row(R.string.api_unbraked_trailer_weight, "Max $data kg")
        Constants.TRAILER_WEIGHT_B -> row(R.string.api_trailer_weight_b, "Max $data kg")
        Constants.TRAILER_WEIGHT_BE -> row(R.string.api_trailer_weight_be, "Max $data kg")
        Constants.CARR_WEIGHT -> row(R.string.api_carriage_weight, "$data kg")
        Constants.TIRE_FRONT -> row(R.string.api_tire_front, data)
        Constants.TIRE_BACK -> row(R.string.api_tire_back, data)
        Constants.RIM_FRONT -> row(R.string.api_rim_front, data)
        Constants.RIM_BACK -> row(R.string.api_rim_back, data)
        Constants.AXEL_WIDTH -> row(R.string.api_axel_width, data)
        Constants.CATEGORY -> row(R.string.api_category, data)
        Constants.EEG -> row(R.string.api_eeg, data)
        Constants.NOX_1 -> row(R.string.api_nox_1, "$data g/km?")
        Constants.THC_NOX_1 -> row(R.string.api_thc_nox_1, "$data g/km?")
        Constants.ECO_CLASS -> row(R.string.api_eco_class, ExplanationHandler().ecoClassType(data))
        Constants.EMISS_CLASS -> row(R.string.api_emission_class, ExplanationHandler().emissionType(data))
        Constants.EURO_NCAP -> row(R.string.api_euro_ncap, data)
        else -> Row("", "")
    }

    private fun row(id: Int, data: String?): Row = Row(desc(id), data)
    private fun desc(id: Int): String = MemoryLeaks.getContext().resources.getString(id)

}
