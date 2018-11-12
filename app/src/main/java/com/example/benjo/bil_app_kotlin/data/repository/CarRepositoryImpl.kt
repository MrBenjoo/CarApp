package com.example.benjo.bil_app_kotlin.data.repository


import android.util.Log
import com.example.benjo.bil_app_kotlin.data.room.CarData
import com.example.benjo.bil_app_kotlin.data.room.CarDataBase


/*
    When a coroutine calls a function marked suspend, instead of blocking until that
    function returns like a normal function call, it suspends execution until
    the result is ready then it resumes where it left off with the result.
    While it's suspended waiting for a result, it unblocks the thread that it's running
    on so other functions or coroutines can run.
*/

class CarRepositoryImpl(private val roomDataSource: CarDataBase) : CarRepository {

    override suspend fun insertCar(carData: CarData) {
        roomDataSource.carDataDao().insert(carData)
    }

    override suspend fun getCar(vin: String): CarData? {
        return roomDataSource.carDataDao().getCar(vin)
    }

    override suspend fun getAllCars(): List<CarData>? {
        return roomDataSource.carDataDao().getAll()
    }

    override suspend fun deleteAll() {
        roomDataSource.carDataDao().deleteAll()
    }

    override suspend fun deleteCar(vin: String) {
        roomDataSource.carDataDao().deleteCar(vin)
    }

    override suspend fun deleteCheckedCars() : Int {
        return roomDataSource.carDataDao().deleteCheckedCars()
    }
}