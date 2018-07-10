package com.example.benjo.bil_app_kotlin.network.json_parsing

import com.example.benjo.bil_app_kotlin.Constants
import com.example.benjo.bil_app_kotlin.MainActivity
import com.example.benjo.bil_app_kotlin.list.model.Row

class JsonHandler(private val activity: MainActivity) {

    fun basicSection(map: Map<String, String?>): ArrayList<Row> {
        val newMap = HashMap<String, String?>()
        with(Constants) {
            newMap[MAKE] = map[MAKE]
            newMap[COLOR] = map[COLOR]
            newMap[TYPE] = map[TYPE]
            newMap[STATUS] = map[STATUS]
            newMap[VEH_YEAR] = map[VEH_YEAR]
            newMap[MOD_YEAR] = map[MOD_YEAR]
        }
        return filterNotNullValues(1, newMap)
    }


    fun otherSection(map: Map<String, String?>): ArrayList<Row> {
        val newMap = HashMap<String, String?>()
        with(Constants) {
            newMap[CATEGORY] = map[CATEGORY]
            newMap[EEG] = map[EEG]
            newMap[NOX] = map[NOX]
            newMap[THC_NOX] = map[THC_NOX]
        }
        return filterNotNullValues(2, newMap)
    }


    fun techSection(map: Map<String, String>): ArrayList<Row> {
        val newMap = HashMap<String, String?>()
        with(Constants) {
            newMap[HP] = map[HP]
            newMap[KW] = map[KW]
            newMap[CYL_VOL] = map[CYL_VOL]
            newMap[TOP_SPEED] = map[TOP_SPEED]
            newMap[FUEL] = map[FUEL]
            newMap[CONSUMPTION] = map[CONSUMPTION]
            newMap[CO2] = map[CO2]
            newMap[TRANSMISSION] = map[TRANSMISSION]
            newMap[FOUR_WHEEL_DRIVE] = map[FOUR_WHEEL_DRIVE]
            newMap[SOUND_LVL] = map[SOUND_LVL]
            newMap[NO_PASSENGERS] = map[NO_PASSENGERS]
            newMap[PASSENGER_AB] = map[PASSENGER_AB]
            newMap[HITCH] = map[HITCH]
        }
        return filterNotNullValues(2, newMap)
    }


    fun dimensionsSection(map: Map<String, String>): ArrayList<Row> {
        val newMap = HashMap<String, String?>()
        with(Constants) {
            newMap[CHASSI_CODE] = map[CHASSI_CODE]
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
        return filterNotNullValues(2, newMap)
    }

    private fun filterNotNullValues(section: Int, map: Map<String, String?>): ArrayList<Row> {
        val result = ArrayList<Row>()

        with(JsonMapping(activity)) {
            for ((key, value) in map)
                when (section) {
                    1 -> if (value != null) result.add(basicInfoMapping(key, value))
                    2 -> if (value != null) result.add(techInfoMapping(key, value))
                }
        }
        return result
    }

}