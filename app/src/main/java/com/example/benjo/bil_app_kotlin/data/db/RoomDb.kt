package com.example.benjo.bil_app_kotlin.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration
import com.example.benjo.bil_app_kotlin.data.db.model.CarData
import com.example.benjo.bil_app_kotlin.data.db.model.RoomDbDao


@Database(entities = arrayOf(CarData::class), version = 3)
abstract class RoomDb : RoomDatabase() {

    abstract fun carDataDao(): RoomDbDao

    /**
     * each RoomDatabase instance is fairly expensive, and you rarely need access to multiple instances. That why
     * I use singleton
     */
    companion object {
        private var INSTANCE: RoomDb? = null

        fun getInstance(context: Context): RoomDb? {
            if (INSTANCE == null) {
                synchronized(RoomDb::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDb::class.java, "cars.db")
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