package com.example.benjo.bil_app_kotlin.utils

import com.example.benjo.bil_app_kotlin.data.network.model.CarInfo
import com.example.benjo.bil_app_kotlin.data.network.model.SearchResponse
import com.example.benjo.bil_app_kotlin.data.network.model.Technical
import com.example.benjo.bil_app_kotlin.data.network.model.TechnicalInfo
import com.example.benjo.bil_app_kotlin.ui.compare.data.model.*


class JsonCompare {

    fun setupCompareJson(car1SearchResponse: SearchResponse?, car2SearchResponse: SearchResponse?): Compare? =
            Compare(setupCarOne(car1SearchResponse), setupCarTwo(car2SearchResponse))


    private fun setupCarOne(car1SearchResponse: SearchResponse?): CompareData? =
            CompareData(setupVehicleData(car1SearchResponse?.carInfo),
                    setupTechnicalData(car1SearchResponse?.carInfo?.technical),
                    setupDimensionData(car1SearchResponse?.carInfo?.technical),
                    car1SearchResponse?.carInfo?.basic?.data?.model)

    private fun setupCarTwo(car2SearchResponse: SearchResponse?): CompareData? =
            CompareData(setupVehicleData(car2SearchResponse?.carInfo),
                    setupTechnicalData(car2SearchResponse?.carInfo?.technical),
                    setupDimensionData(car2SearchResponse?.carInfo?.technical),
                    car2SearchResponse?.carInfo?.basic?.data?.model)


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

    private fun setupMotorData(data: TechnicalInfo?): CompareMotor? {
        return CompareMotor(data?.power_hp_1, data?.power_kw_1,
                data?.cylinder_volume, data?.top_speed)
    }

    private fun setupEnvironmentData(data: TechnicalInfo?): CompareEnvironment? {
        if (data != null) {
            with(data) {
                return CompareEnvironment(fuel_1, consumption_1, co2_1, transmission,
                        four_wheel_drive, nox_1, thc_nox_1, eco_class)
            }
        }
        return null
    }

    private fun setupTechOtherData(data: TechnicalInfo?): CompareTechnicalOther? {
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

    private fun setupWheels(data: TechnicalInfo?): CompareWheels = with(data!!) {
        CompareWheels(tire_front, tire_back, rim_front, rim_back)
    }

    private fun setupWeights(data: TechnicalInfo?): CompareWeights = with(data!!) {
        CompareWeights(kerb_weight,
                total_weight,
                load_weight,
                trailer_weight,
                trailer_weight_b,
                trailer_weight_be,
                unbraked_trailer_weight,
                carriage_weight)
    }

    private fun setupDimensOtherData(data: TechnicalInfo?): CompareDimensionOther? {
        return CompareDimensionOther(data?.length, data?.width, data?.height, data?.axel_width)
    }


}
