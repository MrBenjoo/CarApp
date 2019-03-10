package com.example.benjo.bil_app_kotlin.data.db.repository


import com.example.benjo.bil_app_kotlin.data.db.model.CarData
import com.example.benjo.bil_app_kotlin.data.db.model.RoomDbDao


/*
    When a coroutine calls a function marked suspend, instead of blocking until that
    function returns like a normal function call, it suspends execution until
    the result is ready then it resumes where it left off with the result.
    While it's suspended waiting for a result, it unblocks the thread that it's running
    on so other functions or coroutines can run.
*/

class CarRepositoryImpl(private val roomDbDao: RoomDbDao) : CarRepository {

    override suspend fun insertCar(carData: CarData) = roomDbDao.insert(carData)

    override suspend fun getCar(vin: String): CarData? = roomDbDao.getCar(vin)

    override suspend fun getAllCars(): List<CarData> = roomDbDao.getAll()

    override suspend fun deleteAll() = roomDbDao.deleteAll()

    override suspend fun deleteCar(vin: String) = roomDbDao.deleteCar(vin)

    override suspend fun deleteCheckedCars(): Int = roomDbDao.deleteCheckedCars()

}