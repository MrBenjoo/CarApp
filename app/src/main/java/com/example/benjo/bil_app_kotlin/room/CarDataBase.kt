package com.example.benjo.bil_app_kotlin.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(CarData::class), version = 1)
abstract class CarDataBase : RoomDatabase() {

    abstract fun carDataDao(): CarDataDao

    /**
     * each RoomDatabase instance is fairly expensive, and you rarely need access to multiple instances. That why
     * I use singleton
     */
    companion object {
        private var INSTANCE: CarDataBase? = null

        fun getInstance(context: Context): CarDataBase? {
            if (INSTANCE == null) {
                synchronized(CarDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CarDataBase::class.java, "cars.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}