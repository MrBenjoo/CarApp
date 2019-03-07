package com.example.benjo.bil_app_kotlin.ui.compare.data

import com.example.benjo.bil_app_kotlin.App
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.*
import com.example.benjo.bil_app_kotlin.ui.compare.CompareViewModel
import com.example.benjo.bil_app_kotlin.ui.compare.data.model.*

import com.example.benjo.bil_app_kotlin.utils.ExplanationHandler

class DataProvider(compareViewModel: CompareViewModel) {
    private val compare = compareViewModel.getCompareData()
    private val carOneModel = compareViewModel.getCarOneModel()
    private val carTwoModel = compareViewModel.getCarTwoModel()

    fun getVehicleData(): ArrayList<ItemModel> {
        val listOfItems = arrayListOf<ItemModel>()
        val carOneData = compare.vehicleDataOne()
        val carTwoData = compare.vehicleDataTwo()
        val context = App.getContext()

        with(ExplanationHandler()) {
            listOfItems.add(
                    VehicleModel(
                            context.getString(R.string.title_compare_model_year),
                            carOneModel,
                            carOneData?.modelYear,
                            carTwoModel,
                            carTwoData?.modelYear
                    )
            )
            listOfItems.add(
                    VehicleModel(
                            context.getString(R.string.title_compare_vehicle_year),
                            carOneModel,
                            carOneData?.vehicleYear,
                            carTwoModel,
                            carTwoData?.vehicleYear
                    )
            )
            listOfItems.add(
                    VehicleModel(
                            context.getString(R.string.title_compare_vehicle_type),
                            carOneModel,
                            carOneData?.type,
                            carTwoModel,
                            carTwoData?.type
                    )
            )
            listOfItems.add(
                    VehicleModel(
                            context.getString(R.string.title_compare_reg),
                            carOneModel,
                            carOneData?.reg,
                            carTwoModel,
                            carTwoData?.reg
                    )
            )

            listOfItems.add(
                    VehicleModel(
                            context.getString(R.string.title_compare_status),
                            carOneModel,
                            statusType(carOneData?.status),
                            carTwoModel,
                            statusType(carTwoData?.status)
                    )
            )

            listOfItems.add(
                    VehicleModel(
                            context.getString(R.string.title_compare_color),
                            carOneModel,
                            carOneData?.color,
                            carTwoModel,
                            carTwoData?.color
                    )
            )

            listOfItems.add(
                    VehicleModel(
                            context.getString(R.string.title_compare_chassi),
                            carOneModel,
                            carOneData?.chassi,
                            carTwoModel,
                            carTwoData?.chassi
                    )
            )

            listOfItems.add(
                    VehicleModel(
                            context.getString(R.string.title_compare_category),
                            carOneModel,
                            carOneData?.category,
                            carTwoModel,
                            carTwoData?.category
                    )
            )
            listOfItems.add(
                    VehicleModel(
                            context.getString(R.string.title_compare_eeg),
                            carOneModel,
                            carOneData?.eeg,
                            carTwoModel,
                            carTwoData?.eeg
                    )
            )
        }
        return listOfItems
    }

    fun getTechnicalData(): Map<String, ArrayList<ItemModel>> {
        val map: HashMap<String, ArrayList<ItemModel>> = HashMap()

        map["environment"] = getEnvironmentData(compare.environmentDataOne(), compare.environmentDataTwo())
        map["motor"] = getMotorData(compare.motorDataOne(), compare.motorDataTwo())
        map["other"] = getOtherTechData(compare.otherTechDataOne(), compare.otherTechDataTwo())

        return map
    }

    private fun getEnvironmentData(carOneData: CompareEnvironment?,
                                   carTwoData: CompareEnvironment?): ArrayList<ItemModel> {
        val listOfItems = arrayListOf<ItemModel>()
        val context = App.getContext()

        with(ExplanationHandler()) {
            listOfItems.add(
                    EnvModel(context.getString(R.string.title_compare_fuel),
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
                             carTwoData: CompareMotor?): ArrayList<ItemModel> {
        val listOfItems = arrayListOf<ItemModel>()
        val context = App.getContext()

        listOfItems.add(
                CommonCompareModel(
                        context.getString(R.string.title_compare_horsepower),
                        carOneModel,
                        carOneData?.horsepower,
                        carTwoModel,
                        carTwoData?.horsepower
                )
        )
        listOfItems.add(
                CommonCompareModel(
                        context.getString(R.string.title_compare_kilowatt),
                        carOneModel,
                        carOneData?.kilowatt,
                        carTwoModel,
                        carTwoData?.kilowatt
                )
        )
        listOfItems.add(
                CommonCompareModel(
                        context.getString(R.string.title_compare_cylinder_volume),
                        carOneModel,
                        carOneData?.volume,
                        carTwoModel,
                        carTwoData?.volume
                )
        )
        listOfItems.add(
                CommonCompareModel(
                        context.getString(R.string.title_compare_top_speed),
                        carOneModel,
                        carOneData?.topSpeed,
                        carTwoModel,
                        carTwoData?.topSpeed
                )
        )
        return listOfItems
    }

    private fun getOtherTechData(carOneData: CompareTechnicalOther?,
                                 carTwoData: CompareTechnicalOther?): java.util.ArrayList<ItemModel> {
        val listOfItems = arrayListOf<ItemModel>()
        val context = App.getContext()
        with(ExplanationHandler()) {
            listOfItems.add(
                    OtherTechModel(
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

    fun getDimensionData(): Map<String, ArrayList<ItemModel>> {
        val map: HashMap<String, ArrayList<ItemModel>> = HashMap()

        map["weights"] = getWeightsData(compare.weightsDataOne(), compare.weightsDataTwo())
        map["other"] = getOtherData(compare.otherDimensionsDataOne(), compare.otherDimensionsDataTwo())

        return map
    }

    private fun getOtherData(carOneData: CompareDimensionOther?,
                             carTwoData: CompareDimensionOther?): ArrayList<ItemModel> {
        val listOfItems = arrayListOf<ItemModel>()
        val activity = App.getContext()

        listOfItems.add(
                CommonCompareModel(
                        activity.getString(R.string.title_compare_length),
                        carOneModel,
                        carOneData?.length,
                        carTwoModel,
                        carTwoData?.length
                )
        )
        listOfItems.add(
                CommonCompareModel(
                        activity.getString(R.string.title_compare_width),
                        carOneModel,
                        carOneData?.width,
                        carTwoModel,
                        carTwoData?.width
                )
        )
        listOfItems.add(
                CommonCompareModel(

                        activity.getString(R.string.title_compare_height),
                        carOneModel,
                        carOneData?.height,
                        carTwoModel,
                        carTwoData?.height
                )
        )
        listOfItems.add(
                CommonCompareModel(

                        activity.getString(R.string.title_compare_axel_width),
                        carOneModel,
                        carOneData?.axelWidth,
                        carTwoModel,
                        carTwoData?.axelWidth
                )
        )
        return listOfItems
    }

    private fun getWeightsData(
            weightsDataOne: CompareWeights?,
            weightsDataTwo: CompareWeights?): ArrayList<ItemModel> {
        val listOfItems = arrayListOf<ItemModel>()
        val activity = App.getContext()

        listOfItems.add(
                CommonCompareModel(
                        activity.getString(R.string.title_compare_total_weight),
                        carOneModel,
                        weightsDataOne?.totalWeight,
                        carTwoModel,
                        weightsDataTwo?.totalWeight)
        )

        listOfItems.add(
                CommonCompareModel(
                        activity.getString(R.string.title_compare_load_weight),
                        carOneModel,
                        weightsDataOne?.loadWeight,
                        carTwoModel,
                        weightsDataTwo?.loadWeight)
        )

        listOfItems.add(
                CommonCompareModel(
                        activity.getString(R.string.title_compare_trailer_weight),
                        carOneModel,
                        weightsDataOne?.trailerWeight,
                        carTwoModel,
                        weightsDataTwo?.trailerWeight)
        )

        listOfItems.add(
                CommonCompareModel(
                        activity.getString(R.string.title_compare_unbraked_trailer_weight),
                        carOneModel,
                        weightsDataOne?.unbrakedTrailerWeight,
                        carTwoModel,
                        weightsDataTwo?.unbrakedTrailerWeight)
        )

        listOfItems.add(
                CommonCompareModel(
                        activity.getString(R.string.title_compare_trailer_weight_b),
                        carOneModel,
                        weightsDataOne?.trailerWeightB,
                        carTwoModel,
                        weightsDataTwo?.trailerWeightB)
        )

        listOfItems.add(
                CommonCompareModel(
                        activity.getString(R.string.title_compare_trailer_weight_be),
                        carOneModel,
                        weightsDataOne?.trailerWeightBe,
                        carTwoModel,
                        weightsDataTwo?.trailerWeightBe)
        )
        return listOfItems
    }
}