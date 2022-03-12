package com.example.cleanphone.data.db

import androidx.lifecycle.LiveData
import com.example.cleanphone.domain.model.BatteryStatus


interface DBHelper {
    suspend fun getAllHistoryBattery(): List<BatteryStatus>
}
