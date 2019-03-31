package com.example.benjo.swecar.data

import com.example.benjo.swecar.data.db.model.CarData
import com.example.benjo.swecar.data.network.model.SearchResponse
import com.example.benjo.swecar.ui.compare.data.Normalization
import com.example.benjo.swecar.ui.compare.data.model.*
import com.google.gson.GsonBuilder

fun CarData.deepCopy(): CarData = when (isChecked) {
    true -> CarData(id, reg, model, modelYear, type, vin, json, true)
    false -> CarData(id, reg, model, modelYear, type, vin, json, false)
}

fun Normalization.calculate(): Int {
    val maxValue = getMaxValue(dimension)
    val valueInt: Int
    val valueFloat: Float

    /* 0.17, 177, 0.0019 */
    return try {
        valueInt = value.toInt()
        when (maxValue) {
            null -> 0
            else -> ((valueInt / maxValue) * 100).toInt()
        }
    } catch (exception: NumberFormatException) {
        valueFloat = value.toFloat()
        when (maxValue) {
            null -> 0
            else -> ((valueFloat / maxValue) * 100).toInt()
        }
    }
}

fun ArrayList<CarData>.isAllSelected(): Boolean {
    for (row in this) {
        if (!row.isChecked) return false
    }
    return true
}

fun ArrayList<CarData>.unCheckAll() {
    for (row in this) row.isChecked = false
}

fun ArrayList<CarData>.selectAll() {
    for (row in this) row.isChecked = true
}

fun List<CarData>.toArrayList() : ArrayList<CarData> {
    val arrayList = arrayListOf<CarData>()
    for (item in this) {
        arrayList.add(CarData(null, item.reg, item.model, item.modelYear, item.type, item.vin, item.json))
    }
    return arrayList
}

fun SearchResponse.toCarData(): CarData {
    val reg = carInfo?.attributes?.regno!!
    val model = carInfo.basic?.data?.model!!
    val modelYear = carInfo.basic.data.model_year!!
    val type = carInfo.basic.data.type!!
    val vin = carInfo.attributes.vin!!
    val json = GsonBuilder().create().toJson(this)
    return CarData(null, reg, model, modelYear, type, vin, json)
}

fun SearchResponse.toCompareData(): CompareData =
        CompareData(
                toCompareVehicleData(),
                toCompareTechnicalData(),
                toCompareDimensionData(),
                carInfo?.basic?.data?.model)

fun SearchResponse.toCompareVehicleData(): CompareVehicleData =
        CompareVehicleData(
                carInfo?.basic?.data?.model_year,
                carInfo?.basic?.data?.vehicle_year,
                carInfo?.basic?.data?.type,
                carInfo?.attributes?.regno,
                carInfo?.attributes?.vin,
                carInfo?.basic?.data?.status,
                carInfo?.basic?.data?.color,
                carInfo?.technical?.data?.chassi,
                carInfo?.technical?.data?.category,
                carInfo?.technical?.data?.eeg)


fun SearchResponse.toCompareTechnicalData(): CompareTechnicalData =
        CompareTechnicalData(
                CompareMotor(
                        carInfo?.technical?.data?.power_hp_1,
                        carInfo?.technical?.data?.power_kw_1,
                        carInfo?.technical?.data?.cylinder_volume,
                        carInfo?.technical?.data?.top_speed
                ),
                CompareEnvironment(
                        carInfo?.technical?.data?.fuel_1,
                        carInfo?.technical?.data?.consumption_1,
                        carInfo?.technical?.data?.co2_1,
                        carInfo?.technical?.data?.transmission,
                        carInfo?.technical?.data?.four_wheel_drive,
                        carInfo?.technical?.data?.nox_1,
                        carInfo?.technical?.data?.thc_nox_1,
                        carInfo?.technical?.data?.eco_class
                ),
                CompareTechnicalOther(
                        carInfo?.technical?.data?.sound_level_1,
                        carInfo?.technical?.data?.number_of_passengers,
                        carInfo?.technical?.data?.passenger_airbag,
                        carInfo?.technical?.data?.hitch
                )
        )

fun SearchResponse.toCompareDimensionData(): CompareDimensionData =
        CompareDimensionData(
                CompareWheels(
                        carInfo?.technical?.data?.tire_front,
                        carInfo?.technical?.data?.tire_back,
                        carInfo?.technical?.data?.rim_front,
                        carInfo?.technical?.data?.rim_back
                ),
                CompareWeights(
                        carInfo?.technical?.data?.kerb_weight,
                        carInfo?.technical?.data?.total_weight,
                        carInfo?.technical?.data?.load_weight,
                        carInfo?.technical?.data?.trailer_weight,
                        carInfo?.technical?.data?.trailer_weight_b,
                        carInfo?.technical?.data?.trailer_weight_be,
                        carInfo?.technical?.data?.unbraked_trailer_weight,
                        carInfo?.technical?.data?.carriage_weight
                ),
                CompareDimensionOther(
                        carInfo?.technical?.data?.length,
                        carInfo?.technical?.data?.width,
                        carInfo?.technical?.data?.height,
                        carInfo?.technical?.data?.axel_width
                )
        )





fun Compare.motorDataOne(): CompareMotor? = carOneData?.technical?.motor


fun Compare.motorDataTwo(): CompareMotor? = carTwoData?.technical?.motor


fun Compare.otherTechDataOne(): CompareTechnicalOther? = carOneData?.technical?.other


fun Compare.otherTechDataTwo(): CompareTechnicalOther? = carTwoData?.technical?.other


fun Compare.environmentDataOne(): CompareEnvironment? = carOneData?.technical?.environment


fun Compare.environmentDataTwo(): CompareEnvironment? = carTwoData?.technical?.environment


fun Compare.weightsDataOne(): CompareWeights? = carOneData?.dimensions?.weights


fun Compare.weightsDataTwo(): CompareWeights? = carTwoData?.dimensions?.weights


fun Compare.otherDimensionsDataOne(): CompareDimensionOther? = carOneData?.dimensions?.other


fun Compare.otherDimensionsDataTwo(): CompareDimensionOther? = carTwoData?.dimensions?.other


fun Compare.wheelsDataOne(): CompareWheels? = carOneData?.dimensions?.wheels


fun Compare.wheelsDataTwo(): CompareWheels? = carTwoData?.dimensions?.wheels


fun Compare.vehicleDataOne(): CompareVehicleData? = carOneData?.vehicle


fun Compare.vehicleDataTwo(): CompareVehicleData? = carTwoData?.vehicle