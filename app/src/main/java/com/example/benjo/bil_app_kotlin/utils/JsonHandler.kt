package com.example.benjo.bil_app_kotlin.utils

import android.content.Context
import com.example.benjo.bil_app_kotlin.data.model.KeyValues
import com.example.benjo.bil_app_kotlin.data.model.Row

class JsonHandler(private val context: Context) {

    fun basicSection(map: HashMap<String, String?>?): ArrayList<Row> {
        val newMap = HashMap<String, String?>()
        with(Constants) {
            with(map!!) {
                newMap[MAKE] = get(MAKE)
                newMap[MODEL] = get(MODEL)
                newMap[COLOR] = get(COLOR)
                newMap[TYPE] = get(TYPE)
                newMap[STATUS] = get(STATUS)
                newMap[VEH_YEAR] = get(VEH_YEAR)
                newMap[MOD_YEAR] = get(MOD_YEAR)
            }
        }
        return filterNotNullValues(1, newMap)
    }

    fun techSection(map: HashMap<String, String?>?): ArrayList<Row> {
        val newMap = HashMap<String, String?>()
        with(Constants) {
            with(map!!) {
                with(ExplanationHandler()) {
                    newMap[HP_1] = text(get(HP_1), get(HP_2), get(HP_3))
                    newMap[KW_1] = text(get(KW_1), get(KW_2), get(KW_3))
                    newMap[FUEL_1] = text(fuelType(get(FUEL_1)), fuelType(get(FUEL_2)))
                    newMap[CONSUMPTION_1] = text(get(CONSUMPTION_1), get(CONSUMPTION_2), get(CONSUMPTION_3))
                    newMap[CO2_1] = text(get(CO2_1), get(CO2_2), get(CO2_3))
                    newMap[SOUND_LVL_1] = text(get(SOUND_LVL_1), get(SOUND_LVL_2), get(SOUND_LVL_3))
                    newMap[HITCH] = text(get(HITCH), get(HITCH_2))
                    newMap[CYL_VOL] = get(CYL_VOL)
                    newMap[TOP_SPEED] = get(TOP_SPEED)
                    newMap[TRANSMISSION] = get(TRANSMISSION)
                    newMap[FOUR_WHEEL_DRIVE] = get(FOUR_WHEEL_DRIVE)
                    newMap[NO_PASSENGERS] = get(NO_PASSENGERS)
                    newMap[PASSENGER_AB] = get(PASSENGER_AB)
                }
            }
        }
        return filterNotNullValues(2, newMap)
    }

    fun dimensionsSection(map: HashMap<String, String?>?): ArrayList<Row> {
        val newMap = HashMap<String, String?>()
        with(Constants) {
            with(map!!) {
                newMap[CHASSI_CODE_1] = get(CHASSI_CODE_1)
                newMap[CHASSI] = get(CHASSI)
                newMap[LENGTH] = get(LENGTH)
                newMap[WIDTH] = get(WIDTH)
                newMap[HEIGHT] = get(HEIGHT)
                newMap[CARR_WEIGHT] = get(CARR_WEIGHT)
                newMap[TOTAL_WEIGHT] = get(TOTAL_WEIGHT)
                newMap[TRAILER_WEIGHT] = get(TRAILER_WEIGHT)
                newMap[UNBR_TRAILER_WEIGHT] = get(UNBR_TRAILER_WEIGHT)
                newMap[TRAILER_WEIGHT_B] = get(TRAILER_WEIGHT_B)
                newMap[TRAILER_WEIGHT_BE] = get(TRAILER_WEIGHT_BE)
                newMap[TIRE_FRONT] = get(TIRE_FRONT)
                newMap[TIRE_BACK] = get(TIRE_BACK)
                newMap[RIM_FRONT] = get(RIM_FRONT)
                newMap[RIM_BACK] = get(RIM_BACK)
            }
        }
        return filterNotNullValues(2, newMap)
    }

    fun otherSection(map: HashMap<String, String?>?): ArrayList<Row> {
        val newMap = HashMap<String, String?>()
        with(Constants) {
            with(map!!) {
                newMap[CATEGORY] = get(CATEGORY)
                newMap[EEG] = get(EEG)
                newMap[NOX_1] = text(get(NOX_1), get(NOX_2), get(NOX_3))
                newMap[THC_NOX_1] = text(get(THC_NOX_1), get(THC_NOX_2), get(THC_NOX_3))
            }
        }
        return filterNotNullValues(2, newMap)
    }

    private fun text(value_1: String?, value_2: String?, value_3: String? = ""): String? {
        return KeyValues(value_1, value_2, value_3).toFormattedText()
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