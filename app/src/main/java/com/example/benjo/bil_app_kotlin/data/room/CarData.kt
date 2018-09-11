package com.example.benjo.bil_app_kotlin.data.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "carDataTable")
data class CarData(@PrimaryKey(autoGenerate = true) var id: Long?,
                   @ColumnInfo(name = "reg") var reg: String,
                   @ColumnInfo(name = "model") var model: String,
                   @ColumnInfo(name = "modelYear") var modelYear: String,
                   @ColumnInfo(name = "type") var type: String,
                   @ColumnInfo(name = "vin") var vin: String,
                   @ColumnInfo(name = "json") var json: String)