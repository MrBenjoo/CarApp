package com.example.benjo.bil_app_kotlin.data.repository

import com.example.benjo.bil_app_kotlin.data.room.CarData

interface CarRepository {

    fun insertCar(carData: CarData)

    fun getCar(vin: Int): CarData?

    fun getAllCars(): List<CarData>?

    fun deleteAll()

    fun deleteCar(vin: Int)
}