package com.example.cleanphone.lib

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

import android.os.BatteryManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit


class PowerChangingReceiver : BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(p0: Context?, intent: Intent?) {
        val status: Int = intent!!.getIntExtra(BatteryManager.EXTRA_STATUS, -1)

        val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL
        if (isCharging) {
            Toast.makeText(p0, "The device is charging", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(p0, "The device is not charging", Toast.LENGTH_SHORT).show()
        }

        val batteryUsageWorkRequest =
            PeriodicWorkRequestBuilder<BatteryUsageWorker>(
                PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
                TimeUnit.MILLISECONDS
            )
                .build()

        p0?.let {
            WorkManager.getInstance(it).enqueueUniquePeriodicWork(
                "UK_PERIODIC_BATTERY_USAGE",
                ExistingPeriodicWorkPolicy.REPLACE,
                batteryUsageWorkRequest
            )
        }
    }
}