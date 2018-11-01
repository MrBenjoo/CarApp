package com.example.benjo.bil_app_kotlin.data.model


data class Compare(val car_1: Car1?, val car_2: Car2?)



data class Car1(val vehicle_data: VehData?,
                val technical_data: TechData?)

data class Car2(val vehicle_data: VehData?,
                val technical_data: TechData?)



data class VehData(val vehData : BasicInfo)

data class TechData (val weights : Weights,
                     val dimensions : Dimensions,
                     val wheels: Wheels,
                     val motor : Motor,
                     val fuelEmission : FuelEmission)



data class Weights (val kerb_weight: String?,
                    val total_weight: String?,
                    val load_weight: String?,
                    val trailer_weight: String?,
                    val unbraked_trailer_weight: String?,
                    val trailer_weight_b: String?,
                    val trailer_weight_be: String?,
                    val carriage_weight: String?)

data class Dimensions(val length: String?,
                      val width: String?,
                      val height: String?,
                      val axel_width: String?)

data class Wheels(val tire_front: String?,
                  val tire_back: String?,
                  val rim_front : String?,
                  val rim_back : String?)


data class Motor(val power_hp: String?,
                 val power_kw: String?,
                 val cylinder_volume: String?,
                 val top_speed: String?)

data class FuelEmission (val fuel: String?,
                         val consumption: String?,
                         val co2: String?,
                         val nox: String?,
                         val thc_nox: String?,
                         val eco_class: String?,
                         val emission: String?)








