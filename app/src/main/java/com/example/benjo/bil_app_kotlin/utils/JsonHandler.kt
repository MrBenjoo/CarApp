package com.example.benjo.bil_app_kotlin.utils

import android.content.Context
import com.example.benjo.bil_app_kotlin.data.model.Row

class JsonHandler(private val context: Context) {

    fun basicSection(map: HashMap<String, String?>?): ArrayList<Row> {
        val newMap = HashMap<String, String?>()
        with(Constants) {
            newMap[MAKE] = map?.get(MAKE)
            newMap[COLOR] = map?.get(COLOR)
            newMap[TYPE] = map?.get(TYPE)
            newMap[STATUS] = map?.get(STATUS)
            newMap[VEH_YEAR] = map?.get(VEH_YEAR)
            newMap[MOD_YEAR] = map?.get(MOD_YEAR)
        }
        return filterNotNullValues(1, newMap)
    }


    fun otherSection(map: HashMap<String, String?>?): ArrayList<Row> {
        val newMap = HashMap<String, String?>()
        with(Constants) {
            newMap[CATEGORY] = map?.get(CATEGORY)
            newMap[EEG] = map?.get(EEG)
            newMap[NOX] = map?.get(NOX)
            newMap[THC_NOX] = map?.get(THC_NOX)
        }
        return filterNotNullValues(2, newMap)
    }


    fun techSection(map: HashMap<String, String?>?): ArrayList<Row> {
        val newMap = HashMap<String, String?>()
        with(Constants) {
            newMap[HP] = map?.get(HP)
            newMap[KW] = map?.get(KW)
            newMap[CYL_VOL] = map?.get(CYL_VOL)
            newMap[TOP_SPEED] = map?.get(TOP_SPEED)
            newMap[FUEL] = map?.get(FUEL)
            newMap[CONSUMPTION] = map?.get(CONSUMPTION)
            newMap[CO2] = map?.get(CO2)
            newMap[TRANSMISSION] = map?.get(TRANSMISSION)
            newMap[FOUR_WHEEL_DRIVE] = map?.get(FOUR_WHEEL_DRIVE)
            newMap[SOUND_LVL] = map?.get(SOUND_LVL)
            newMap[NO_PASSENGERS] = map?.get(NO_PASSENGERS)
            newMap[PASSENGER_AB] = map?.get(PASSENGER_AB)
            newMap[HITCH] = map?.get(HITCH)
        }
        return filterNotNullValues(2, newMap)
    }


    fun dimensionsSection(map: HashMap<String, String?>?): ArrayList<Row> {
        val newMap = HashMap<String, String?>()
        with(Constants) {
            newMap[CHASSI_CODE] = map?.get(CHASSI_CODE)
            newMap[CHASSI] = map?.get(CHASSI)
            newMap[LENGTH] = map?.get(LENGTH)
            newMap[WIDTH] = map?.get(WIDTH)
            newMap[HEIGHT] = map?.get(HEIGHT)
            newMap[CARR_WEIGHT] = map?.get(CARR_WEIGHT)
            newMap[TOTAL_WEIGHT] = map?.get(TOTAL_WEIGHT)
            newMap[TRAILER_WEIGHT] = map?.get(TRAILER_WEIGHT)
            newMap[UNBR_TRAILER_WEIGHT] = map?.get(UNBR_TRAILER_WEIGHT)
            newMap[TRAILER_WEIGHT_B] = map?.get(TRAILER_WEIGHT_B)
            newMap[TRAILER_WEIGHT_BE] = map?.get(TRAILER_WEIGHT_BE)
            newMap[TIRE_FRONT] = map?.get(TIRE_FRONT)
            newMap[TIRE_BACK] = map?.get(TIRE_BACK)
            newMap[RIM_FRONT] = map?.get(RIM_FRONT)
            newMap[RIM_BACK] = map?.get(RIM_BACK)
        }
        return filterNotNullValues(2, newMap)
    }

    private fun filterNotNullValues(section: Int, map: Map<String, String?>): ArrayList<Row> {
        val result = ArrayList<Row>()

        with(JsonMapping(context)) {
            for ((key, value) in map)
                when (section) {
                    1 -> if (value != null) result.add(basicInfoMapping(key, value))
                    2 -> if (value != null) result.add(techInfoMapping(key, value))
                }
        }
        return result
    }

}