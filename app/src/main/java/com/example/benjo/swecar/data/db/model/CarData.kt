package com.example.benjo.swecar.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carDataTable")
data class CarData(@PrimaryKey(autoGenerate = true)
                   @ColumnInfo(name = "id") var id: Long?,
                   @ColumnInfo(name = "reg") var reg: String,
                   @ColumnInfo(name = "model") var model: String,
                   @ColumnInfo(name = "modelYear") var modelYear: String,
                   @ColumnInfo(name = "type") var type: String,
                   @ColumnInfo(name = "vin") var vin: String,
                   @ColumnInfo(name = "json") var json: String,
                   @ColumnInfo(name = "isChecked") var isChecked: Boolean = false) 