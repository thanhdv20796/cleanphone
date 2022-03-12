package com.example.cleanphone.lib

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cleanphone.data.model.local.BatteryHealthType
import com.example.cleanphone.data.model.local.BatteryPowerSourceType
import com.example.cleanphone.data.model.local.BatteryStatus
import com.example.cleanphone.data.model.local.BatteryStatusType
import com.google.gson.Gson
import kotlinx.coroutines.coroutineScope
import java.util.*

class BatteryUsageWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    companion object {
        private val TAG = BatteryUsageWorker::class.java.simpleName
    }

    override suspend fun doWork(): Result = coroutineScope {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            applicationContext.registerReceiver(null, ifilter)
        }
        val manager = applicationContext.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val bStatus = BatteryStatus(
            date = Date(),
            health = BatteryHealthType.getByValue(
                batteryStatus?.getIntExtra(
                    BatteryManager.EXTRA_HEALTH,
                    1
                ) ?: 1
            ),
            currentLevel = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1,
            powerSource = BatteryPowerSourceType.getByValue(
                batteryStatus?.getIntExtra(
                    BatteryManager.EXTRA_PLUGGED,
                    0
                ) ?: 0
            ),
            isPresent = batteryStatus?.getBooleanExtra(BatteryManager.EXTRA_PRESENT, true) ?: true,
            maxLevel = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1,
            status = BatteryStatusType.getByValue(
                batteryStatus?.getIntExtra(
                    BatteryManager.EXTRA_STATUS,
                    1
                ) ?: 1
            ),
            technology = batteryStatus?.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY) ?: "",
            temperature = batteryStatus?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) ?: -1,
            voltageLevel = batteryStatus?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) ?: -1,
            capacityRemainingPercentage = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY),
            capacityInMicroampereHours = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER),
            averageMicroamperes = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE),
            currentMicroamperes = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW),
            remainingEnergyNanowattHour = manager.getLongProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER),
        )
//        val id = AppDatabase.getInstance(applicationContext).batteryDao().insert(bStatus)
//        if(id <= 0) {
//            Log.wtf(TAG, "Persisting error")
//            Result.retry()
//        }
        Log.wtf(TAG, Gson().toJson(bStatus))
        Result.success()
    }

}