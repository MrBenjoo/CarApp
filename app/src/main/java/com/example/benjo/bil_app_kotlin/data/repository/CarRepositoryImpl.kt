package com.example.benjo.bil_app_kotlin.data.repository

import com.example.benjo.bil_app_kotlin.data.repository.CarRepository
import com.example.benjo.bil_app_kotlin.data.room.CarData
import com.example.benjo.bil_app_kotlin.data.room.CarDataBase
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking

class CarRepositoryImpl(private val roomDataSource: CarDataBase) : CarRepository {

    override fun insertCar(carData: CarData) {
        runAsync { roomDataSource.carDataDao().insert(carData) }
    }

    override fun getCar(vin: Int): CarData? {
        var car: CarData? = null
        runAsync { car = roomDataSource.carDataDao().getCar(vin) }
        return car
    }

    override fun getAllCars(): List<CarData>? {
        var list: List<CarData>? = null
        runAsync { list = roomDataSource.carDataDao().getAll() }
        return list
    }

    override fun deleteAll() {
        runAsync { roomDataSource.carDataDao().deleteAll() }
    }

    override fun deleteCar(vin: Int) {
        runAsync { roomDataSource.carDataDao().deleteCar(vin) }
    }

    fun runAsync(function: () -> Unit) {
        runBlocking {
            async(CommonPool) {
                function()
            }.await()
        }
    }


}