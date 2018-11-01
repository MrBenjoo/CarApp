package com.example.benjo.bil_app_kotlin.utils

import com.example.benjo.bil_app_kotlin.data.model.*


class JsonCompare {

    fun setupCompareJson(car1Result: Result?, car2Result: Result?): Compare? =
            Compare(setupCarOne(car1Result), setupCarTwo(car2Result))

    private fun setupCarOne(car1Result: Result?): Car1? =
            Car1(setupVehicleData(car1Result?.carInfo?.basic),
                    setupTechnicalData(car1Result?.carInfo?.technical))

    private fun setupCarTwo(car2Result: Result?): Car2? =
            Car2(setupVehicleData(car2Result?.carInfo?.basic),
                    setupTechnicalData(car2Result?.carInfo?.technical))

    private fun setupVehicleData(basic: Basic?): VehData? = with(basic?.data!!) {
        VehData(BasicInfo(make, model, status, color, type, vehicle_year, model_year))
    }

    private fun setupTechnicalData(carResult: Technical?): TechData? {
        return TechData(setupWeights(carResult?.data),
                setupDimensions(carResult?.data),
                setupWheels(carResult?.data),
                setupMotor(carResult?.data),
                setupFuelEmission(carResult?.data))
    }

    private fun setupWeights(data: TechnicalData?): Weights = with(data!!) {
        Weights(kerb_weight,
                total_weight,
                load_weight,
                trailer_weight,
                unbraked_trailer_weight,
                trailer_weight_b,
                trailer_weight_be,
                carriage_weight)
    }

    private fun setupDimensions(data: TechnicalData?): Dimensions = with(data!!) {
        Dimensions(length, width, height, axel_width)
    }

    private fun setupWheels(data: TechnicalData?): Wheels = with(data!!) {
        Wheels(tire_front, tire_back, rim_front, rim_back)
    }

    private fun setupMotor(data: TechnicalData?): Motor = with(data!!) {
        Motor(power_hp_1, power_kw_1, cylinder_volume, top_speed)
    }

    private fun setupFuelEmission(data: TechnicalData?): FuelEmission = with(data!!) {
        FuelEmission(fuel_1, consumption_1, co2_1, nox_1, thc_nox_1, eco_class, emission_class)
    }
}