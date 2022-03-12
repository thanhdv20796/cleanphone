package com.example.cleanphone.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cleanphone.data.model.local.BatteryStatus

@Dao
interface BatteryStatusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bStatus: BatteryStatus): Long

    @Query("DELETE FROM BatteryStatus")
    suspend fun deleteAll()

    @Query("SELECT * FROM BatteryStatus ORDER BY date desc")
    suspend fun getAll(): List<BatteryStatus>

    @Query("SELECT * FROM BatteryStatus WHERE date BETWEEN :dateStart AND :dateEnt ORDER BY date desc")
    fun getBetweenDates(dateStart: Long, dateEnt: Long): LiveData<List<BatteryStatus>>
}