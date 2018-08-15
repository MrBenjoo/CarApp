package com.example.benjo.bil_app_kotlin.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.example.benjo.bil_app_kotlin.room.CarData

@Dao
interface CarDataDao {

    @Query("SELECT * from carDataTable")
    fun getAll(): List<CarData>

    @Insert(onConflict = REPLACE)
    fun insert(carData: CarData)

    @Query("DELETE from carDataTable")
    fun deleteAll()

}