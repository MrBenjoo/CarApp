package com.example.benjo.bil_app_kotlin.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration
import com.example.benjo.bil_app_kotlin.data.db.model.CarData


@Database(entities = arrayOf(CarData::class), version = 3)
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
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE 'carDataTable' ADD COLUMN 'vin' TEXT");
            }
        }
    }


}