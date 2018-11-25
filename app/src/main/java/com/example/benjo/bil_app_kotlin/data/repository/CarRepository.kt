package com.example.benjo.bil_app_kotlin.data.repository

import com.example.benjo.bil_app_kotlin.data.room.CarData

interface CarRepository {

    suspend fun insertCar(carData: CarData)

    suspend fun getCar(vin: String): CarData?

    suspend fun getAllCars(): List<CarData>

    suspend fun deleteAll()

    suspend fun deleteCar(vin: String)

    suspend fun deleteCheckedCars() : Int

}