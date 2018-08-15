package com.example.benjo.bil_app_kotlin.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "carDataTable")
data class CarData(@PrimaryKey(autoGenerate = true) var id: Long?,
                   @ColumnInfo(name = "vin") var vin: Int,
                   @ColumnInfo(name = "json") var json: String){
}