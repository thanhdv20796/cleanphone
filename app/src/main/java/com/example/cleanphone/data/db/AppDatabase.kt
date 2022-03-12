package com.example.cleanphone.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cleanphone.domain.model.BatteryStatus


@Database(entities = [BatteryStatus::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun batteryDao(): BatteryStatusDao


}