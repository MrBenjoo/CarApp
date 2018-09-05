package com.example.benjo.bil_app_kotlin.data.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query


@Dao
interface CarDataDao {

    @Query("SELECT * from carDataTable")
    fun getAll(): List<CarData>?

    @Insert(onConflict = REPLACE)
    fun insert(carData: CarData)

    @Query("DELETE from carDataTable")
    fun deleteAll()

    @Query("SELECT * from carDataTable WHERE vin = :vin")
    fun getCar(vin: Int): CarData?

    @Query("DELETE from carDataTable WHERE vin = :vin")
    fun deleteCar(vin: Int)

}