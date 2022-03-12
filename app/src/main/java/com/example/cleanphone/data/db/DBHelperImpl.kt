package com.example.cleanphone.data.db

import com.example.cleanphone.data.model.local.BatteryStatus
import javax.inject.Inject

class DBHelperImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : DBHelper {
    override suspend fun getAllHistoryBattery(): List<BatteryStatus> = appDatabase.batteryDao().getAll()

}
