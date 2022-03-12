package com.example.cleanphone.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cleanphone.data.db.battery.BatteryStatusDao
import com.example.cleanphone.data.model.local.BatteryStatus


@Database(entities = [BatteryStatus::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun batteryDao(): BatteryStatusDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "clean_phone_db")
                .allowMainThreadQueries()
                .build()
    }
}