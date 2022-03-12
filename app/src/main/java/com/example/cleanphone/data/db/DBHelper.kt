package com.example.cleanphone.data.db

import com.example.cleanphone.data.model.local.BatteryStatus

interface DBHelper {
    suspend fun getAllHistoryBattery(): List<BatteryStatus>
}
