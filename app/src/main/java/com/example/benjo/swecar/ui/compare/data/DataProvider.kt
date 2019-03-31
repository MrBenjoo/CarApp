package com.example.benjo.swecar.ui.compare.data

import com.example.benjo.swecar.App
import com.example.benjo.swecar.R
import com.example.benjo.swecar.data.*
import com.example.benjo.swecar.MainViewModel
import com.example.benjo.swecar.ui.compare.data.model.*

import com.example.benjo.swecar.utils.ExplanationHandler

class DataProvider(mainViewModel: MainViewModel) {
    private val compare = mainViewModel.getCompareData()
    private val carOneModel = mainViewModel.getCarOneModel()
    private val carTwoModel = mainViewModel.getCarTwoModel()
    private var weightsTitleArray: Array<String>
    private var vehicleTitleArray: Array<String>
    private var motorTitleArray: Array<String>

    private var otherDimensionTitle: Array<String>


    init {
        weightsTitleArray = arrayOf(
                title(R.string.title_compare_total_weight),
                title(R.string.title_compare_load_weight),
                title(R.string.title_compare_trailer_weight),
                title(R.string.title_compare_unbraked_trailer_weight),
                title(R.string.title_compare_trailer_weight_b),
                title(R.string.title_compare_trailer_weight_be)
        )

        vehicleTitleArray = arrayOf(
                title(R.string.title_compare_model_year),
                title(R.string.title_compare_vehicle_year),
                title(R.string.title_compare_vehicle_type),
                title(R.string.title_compare_reg),
                title(R.string.title_compare_status),
                title(R.string.title_compare_color),
                title(R.string.title_compare_chassi),
                title(R.string.title_compare_category),
                title(R.string.title_compare_eeg)
        )

        motorTitleArray = arrayOf(
                title(R.string.title_compare_horsepower),
                title(R.string.title_compare_kilowatt),
                title(R.string.title_compare_cylinder_volume),
                title(R.string.title_compare_top_speed)
        )

        otherDimensionTitle = arrayOf(
                title(R.string.title_compare_length),
                title(R.string.title_compare_width),
                title(R.string.title_compare_height),
                title(R.string.title_compare_axel_width)
        )
    }

    private fun getProperWeightData(title: String, weightsData: CompareWeights?): String? = when (title) {
        title(R.string.title_compare_total_weight) -> weightsData?.totalWeight
        title(R.string.title_compare_load_weight) -> weightsData?.loadWeight
        title(R.string.title_compare_trailer_weight) -> weightsData?.trailerWeight
        title(R.string.title_compare_unbraked_trailer_weight) -> weightsData?.unbrakedTrailerWeight
        title(R.string.title_compare_trailer_weight_b) -> weightsData?.trailerWeightB
        title(R.string.title_compare_trailer_weight_be) -> weightsData?.trailerWeightBe
        else -> ""
    }

    private fun getProperVehicleData(title: String, vehicleData: CompareVehicleData?): String? = when (title) {
        title(R.string.title_compare_model_year) -> vehicleData?.modelYear
        title(R.string.title_compare_vehicle_year) -> vehicleData?.vehicleYear
        title(R.string.title_compare_vehicle_type) -> vehicleData?.type
        title(R.string.title_compare_reg) -> vehicleData?.reg
        title(R.string.title_compare_status) -> vehicleData?.status
        title(R.string.title_compare_color) -> vehicleData?.color
        title(R.string.title_compare_chassi) -> vehicleData?.chassi
        title(R.string.title_compare_category) -> vehicleData?.category
        title(R.string.title_compare_eeg) -> vehicleData?.eeg
        else -> ""
    }

    private fun getProperMotorData(title: String, motorData: CompareMotor?): String? = when (title) {
        title(R.string.title_compare_horsepower) -> motorData?.horsepower
        title(R.string.title_compare_kilowatt) -> motorData?.kilowatt
        title(R.string.title_compare_cylinder_volume) -> motorData?.volume
        title(R.string.title_compare_top_speed) -> motorData?.topSpeed
        else -> title
    }


    private fun getProperOtherDimensionData(title: String, otherDimensionData: CompareDimensionOther?): String? = when (title) {
        title(R.string.title_compare_length) -> otherDimensionData?.length
        title(R.string.title_compare_width) -> otherDimensionData?.width
        title(R.string.title_compare_height) -> otherDimensionData?.height
        title(R.string.title_compare_axel_width) -> otherDimensionData?.axelWidth
        else -> title
    }


    private fun getWeightsData(weightsDataOne: CompareWeights?, weightsDataTwo: CompareWeights?): ArrayList<ItemCompareModel> {
        val listOfItems = arrayListOf<ItemCompareModel>()

        return weightsTitleArray.indices
                .asSequence()
                .map { weightsTitleArray[it] }
                .mapTo(listOfItems) {
                    CommonCompareModel(
                            it,
                            carOneModel,
                            getProperWeightData(it, weightsDataOne),
                            carTwoModel,
                            getProperWeightData(it, weightsDataTwo)
                    )
                }
    }

    fun getVehicleData(): ArrayList<ItemCompareModel> {
        val carOneData = compare.vehicleDataOne()
        val carTwoData = compare.vehicleDataTwo()
        val listTest = arrayListOf<ItemCompareModel>()

        vehicleTitleArray.indices
                .asSequence()
                .map { vehicleTitleArray[it] }
                .mapTo(listTest) {
                    VehicleCompareModel(
                            it,
                            carOneModel,
                            getProperVehicleData(it, carOneData),
                            carTwoModel,
                            getProperVehicleData(it, carTwoData)
                    )
                }
        return listTest
    }

    fun getTechnicalData(): Map<String, ArrayList<ItemCompareModel>> {
        val map: HashMap<String, ArrayList<ItemCompareModel>> = HashMap()

        map["environment"] = getEnvironmentData(compare.environmentDataOne(), compare.environmentDataTwo())
        map["motor"] = getMotorData(compare.motorDataOne(), compare.motorDataTwo())
        map["other"] = getOtherTechData(compare.otherTechDataOne(), compare.otherTechDataTwo())

        return map
    }

    private fun getEnvironmentData(carOneData: CompareEnvironment?,
                                   carTwoData: CompareEnvironment?): ArrayList<ItemCompareModel> {
        val listOfItems = arrayListOf<ItemCompareModel>()
        val context = App.getContext()

        with(ExplanationHandler()) {
            listOfItems.add(
                    EnvironmentCompareModel(context.getString(R.string.title_compare_fuel),
                            carOneModel,
                            fuelType(carOneData?.fuel),
                            carTwoModel,
                            fuelType(carTwoData?.fuel),

                            context.getString(R.string.title_compare_eco_class),
                            carOneModel,
                            ecoClassType(carOneData?.ecoClass),
                            carTwoModel,
                            ecoClassType(carTwoData?.ecoClass),

                            context.getString(R.string.title_compare_four_wheel_drive),
                            carOneModel,
                            boolType(carOneData?.fourWheelDrive),
                            carTwoModel,
                            boolType(carTwoData?.fourWheelDrive),

                            context.getString(R.string.title_compare_transmission),
                            carOneModel,
                            transType(carOneData?.transmission),
                            carTwoModel,
                            transType(carTwoData?.transmission)
                    ))

            listOfItems.add(
                    CommonCompareModel(
                            context.getString(R.string.title_compare_consumption),
                            carOneModel,
                            carOneData?.consumption,
                            carTwoModel,
                            carTwoData?.consumption)
            )

            listOfItems.add(
                    CommonCompareModel(
                            context.getString(R.string.title_compare_co2),
                            carOneModel,
                            carOneData?.co2,
                            carTwoModel,
                            carTwoData?.co2)
            )

            listOfItems.add(
                    CommonCompareModel(
                            context.getString(R.string.title_compare_nox),
                            carOneModel,
                            carOneData?.nox,
                            carTwoModel,
                            carTwoData?.nox)
            )

            listOfItems.add(
                    CommonCompareModel(
                            context.getString(R.string.title_compare_thc_nox),
                            carOneModel,
                            carOneData?.thcNox,
                            carTwoModel,
                            carTwoData?.thcNox)
            )
        }
        return listOfItems
    }


    private fun getMotorData(carOneData: CompareMotor?,
                             carTwoData: CompareMotor?): ArrayList<ItemCompareModel> {
        val listOfItems = arrayListOf<ItemCompareModel>()

        return motorTitleArray.indices
                .asSequence()
                .map { motorTitleArray[it] }
                .mapTo(listOfItems) {
                    CommonCompareModel(
                            it,
                            carOneModel,
                            getProperMotorData(it, carOneData),
                            carTwoModel,
                            getProperMotorData(it, carTwoData)
                    )
                }
    }


    private fun getOtherTechData(carOneData: CompareTechnicalOther?,
                                 carTwoData: CompareTechnicalOther?): java.util.ArrayList<ItemCompareModel> {
        val listOfItems = arrayListOf<ItemCompareModel>()
        val context = App.getContext()
        with(ExplanationHandler()) {
            listOfItems.add(
                    OtherTechCompareModel(
                            context.getString(R.string.title_compare_number_of_passengers),
                            carOneModel,
                            carOneData?.passengers,
                            carTwoModel,
                            carTwoData?.passengers,

                            context.getString(R.string.title_compare_passenger_airbag),
                            carOneModel,
                            boolType(carOneData?.passengerAirbag),
                            carTwoModel,
                            boolType(carTwoData?.passengerAirbag),

                            context.getString(R.string.title_compare_hitch),
                            carOneModel,
                            carOneData?.hitch,
                            carTwoModel,
                            carTwoData?.hitch)
            )
        }

        listOfItems.add(
                CommonCompareModel(
                        context.getString(R.string.title_compare_sound_level),
                        carOneModel,
                        carOneData?.soundLevel,
                        carTwoModel,
                        carTwoData?.soundLevel)
        )
        return listOfItems
    }

    fun getDimensionData(): Map<String, ArrayList<ItemCompareModel>> {
        val map: HashMap<String, ArrayList<ItemCompareModel>> = HashMap()

        map["weights"] = getWeightsData(compare.weightsDataOne(), compare.weightsDataTwo())
        map["other"] = getOtherData(compare.otherDimensionsDataOne(), compare.otherDimensionsDataTwo())

        return map
    }

    private fun getOtherData(carOneData: CompareDimensionOther?,
                             carTwoData: CompareDimensionOther?): ArrayList<ItemCompareModel> {
        val listOfItems = arrayListOf<ItemCompareModel>()

        return otherDimensionTitle.indices
                .asSequence()
                .map { otherDimensionTitle[it] }
                .mapTo(listOfItems) {
                    CommonCompareModel(
                            it,
                            carOneModel,
                            getProperOtherDimensionData(it, carOneData),
                            carTwoModel,
                            getProperOtherDimensionData(it, carTwoData)
                    )
                }
    }


    private fun title(id: Int): String = App.getContext().getString(id)


}