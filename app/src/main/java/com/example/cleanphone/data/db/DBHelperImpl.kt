package com.example.cleanphone.data.db

import androidx.lifecycle.LiveData
import com.example.cleanphone.data.db.DBHelper
import com.example.cleanphone.domain.model.BatteryStatus
import javax.inject.Inject

class DBHelperImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : DBHelper {
    override suspend fun getAllHistoryBattery(): List<BatteryStatus> = appDatabase.batteryDao().getAll()

}
