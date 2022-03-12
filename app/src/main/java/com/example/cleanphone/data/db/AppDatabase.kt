package com.example.cleanphone.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cleanphone.data.model.local.BatteryStatus


@Database(entities = [BatteryStatus::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun batteryDao(): BatteryStatusDao
}