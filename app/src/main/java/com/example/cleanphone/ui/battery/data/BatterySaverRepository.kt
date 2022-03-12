package com.example.cleanphone.ui.battery.data

import com.example.cleanphone.data.db.DBHelper
import com.example.cleanphone.data.remote.ApiHelper
import com.example.cleanphone.di.NetworkModule
import com.example.cleanphone.data.model.local.BatteryStatus
import javax.inject.Inject


interface BatterySaverRepositoryHelper : DBHelper {

}

class BatterySaverRepositoryImp @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dbHelper: DBHelper,
    private val network: NetworkModule.Network
) : BatterySaverRepositoryHelper {

    override suspend fun getAllHistoryBattery(): List<BatteryStatus> =
        dbHelper.getAllHistoryBattery();

}