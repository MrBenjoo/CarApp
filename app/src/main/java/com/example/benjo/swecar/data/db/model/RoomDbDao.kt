package com.example.benjo.swecar.data.db.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query


@Dao
interface RoomDbDao {

    @Query("SELECT * from carDataTable")
    fun getAll(): List<CarData>

    @Insert(onConflict = REPLACE)
    fun insert(carData: CarData)

    @Query("DELETE from carDataTable")
    fun deleteAll()

    @Query("SELECT * from carDataTable WHERE vin = :vin")
    fun getCar(vin: String): CarData?

    @Query("DELETE from carDataTable WHERE vin = :vin")
    fun deleteCar(vin: String)

    @Query("DELETE FROM carDataTable WHERE isChecked like 1")
    fun deleteCheckedCars(): Int


}