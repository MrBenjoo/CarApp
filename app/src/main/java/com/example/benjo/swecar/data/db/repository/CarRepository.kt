package com.example.benjo.swecar.data.db.repository

import com.example.benjo.swecar.data.db.model.CarData

interface CarRepository {

    suspend fun insertCar(carData: CarData)

    suspend fun getCar(vin: String): CarData?

    suspend fun getAllCars(): List<CarData>

    suspend fun deleteAll()

    suspend fun deleteCar(vin: String)

    suspend fun deleteCheckedCars(): Int

}