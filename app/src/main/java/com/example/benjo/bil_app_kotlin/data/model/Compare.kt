package com.example.benjo.bil_app_kotlin.data.model


data class Compare(val carOneData: CompareData?, val carTwoData: CompareData?)

data class CompareData(val vehicle: CompareVehicleData?,
                       val technical: CompareTechnicalData?,
                       val dimensions: CompareDimensionData?)

data class CompareVehicleData(val modelYear: String?,
                              val vehicleYear: String?,
                              val type: String?,
                              val reg: String?,
                              val vin: String?,
                              val status: String?,
                              val color: String?,
                              val chassi: String?,
                              val category: String?,
                              val eeg: String?)

data class CompareTechnicalData(val motor: CompareMotor?,
                                val environment: CompareEnvironment?,
                                val other: CompareTechnicalOther?)

data class CompareMotor(val horsepower: String?,
                        val kilowatt: String?,
                        val volume: String?,
                        val topSpeed: String?)

data class CompareEnvironment(val fuel: String?,
                              val consumption: String?,
                              val co2: String?,
                              val transmission: String?,
                              val fourWheelDrive: String?,
                              val nox: String?,
                              val thcNox: String?,
                              val ecoClass: String?)

data class CompareTechnicalOther(val soundLevel: String?,
                                 val passengers: String?,
                                 val passengerAirbag: String?,
                                 val hitch: String?)

data class CompareDimensionData(val wheels: CompareWheels?,
                                val weights: CompareWeights?,
                                val other: CompareDimensionOther?)

data class CompareWheels(val tireFront: String?,
                         val tireBack: String?,
                         val rimFront: String?,
                         val rimBack: String?)

data class CompareWeights(val kerbWeight: String?,
                          val totalWeight: String?,
                          val loadWeight: String?,
                          val trailerWeight: String?,
                          val trailerWeightB: String?,
                          val trailerWeightBe: String?,
                          val unbrakedTrailerWeight: String?,
                          val carriageWeight: String?)

data class CompareDimensionOther(val length: String?,
                                 val width: String?,
                                 val height: String?,
                                 val axelWidth: String?)



