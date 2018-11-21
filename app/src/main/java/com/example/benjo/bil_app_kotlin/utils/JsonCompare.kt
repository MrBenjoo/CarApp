package com.example.benjo.bil_app_kotlin.utils

import com.example.benjo.bil_app_kotlin.data.model.*
import com.example.benjo.bil_app_kotlin.domain.*
import com.example.benjo.bil_app_kotlin.domain.Technical


class JsonCompare {

    fun setupCompareJson(car1Result: Result?, car2Result: Result?): Compare? =
            Compare(setupCarOne(car1Result), setupCarTwo(car2Result))


    private fun setupCarOne(car1Result: Result?): CompareData? =
            CompareData(setupVehicleData(car1Result?.carInfo),
                    setupTechnicalData(car1Result?.carInfo?.technical),
                    setupDimensionData(car1Result?.carInfo?.technical),
                    car1Result?.carInfo?.basic?.data?.model)

    private fun setupCarTwo(car2Result: Result?): CompareData? =
            CompareData(setupVehicleData(car2Result?.carInfo),
                    setupTechnicalData(car2Result?.carInfo?.technical),
                    setupDimensionData(car2Result?.carInfo?.technical),
                    car2Result?.carInfo?.basic?.data?.model)


    private fun setupVehicleData(carInfo: CarInfo?): CompareVehicleData? {
        val basic = carInfo?.basic?.data
        val attributes = carInfo?.attributes
        val tech = carInfo?.technical?.data

        return CompareVehicleData(basic?.model_year, basic?.vehicle_year, basic?.type,
                attributes?.regno, attributes?.vin,
                basic?.status, basic?.color, tech?.chassi, tech?.category, tech?.eeg)
    }

    private fun setupTechnicalData(carResult: Technical?): CompareTechnicalData? {
        return CompareTechnicalData(setupMotorData(carResult?.data),
                setupEnvironmentData(carResult?.data),
                setupTechOtherData(carResult?.data))
    }

    private fun setupMotorData(data: TechnicalData?): CompareMotor? {
        return CompareMotor(data?.power_hp_1, data?.power_kw_1,
                data?.cylinder_volume, data?.top_speed)
    }

    private fun setupEnvironmentData(data: TechnicalData?): CompareEnvironment? {
        if (data != null) {
            with(data) {
                return CompareEnvironment(fuel_1, consumption_1, co2_1, transmission,
                        four_wheel_drive, nox_1, thc_nox_1, eco_class)
            }
        }
        return null
    }

    private fun setupTechOtherData(data: TechnicalData?): CompareTechnicalOther? {
        if (data != null) {
            with(data) {
                return CompareTechnicalOther(sound_level_1, number_of_passengers, passenger_airbag, hitch)
            }
        }
        return null
    }


    private fun setupDimensionData(technical: Technical?): CompareDimensionData? {
        return CompareDimensionData(setupWheels(technical?.data),
                setupWeights(technical?.data),
                setupDimensOtherData(technical?.data))
    }

    private fun setupWheels(data: TechnicalData?): CompareWheels = with(data!!) {
        CompareWheels(tire_front, tire_back, rim_front, rim_back)
    }

    private fun setupWeights(data: TechnicalData?): CompareWeights = with(data!!) {
        CompareWeights(kerb_weight,
                total_weight,
                load_weight,
                trailer_weight,
                trailer_weight_b,
                trailer_weight_be,
                unbraked_trailer_weight,
                carriage_weight)
    }

    private fun setupDimensOtherData(data: TechnicalData?): CompareDimensionOther? {
        return CompareDimensionOther(data?.length, data?.width, data?.height, data?.axel_width)
    }


}