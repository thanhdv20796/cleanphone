package com.example.cleanphone.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.cleanphone.data.db.Converters
import java.util.*


@Entity
@TypeConverters(Converters::class)
data class BatteryStatus(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val date: Date = Calendar.getInstance().time,
    val health: BatteryHealthType,
    val currentLevel: Int,
    val powerSource: BatteryPowerSourceType,
    val isPresent: Boolean,
    val maxLevel: Int,
    val status: BatteryStatusType,
    val technology: String,
    val temperature: Int,
    val voltageLevel: Int,
    val capacityRemainingPercentage: Int,
    val capacityInMicroampereHours: Int,
    val averageMicroamperes: Int,
    val currentMicroamperes: Int,
    val remainingEnergyNanowattHour: Long
)

enum class BatteryHealthType(val value: Int) {

    COLD(7),
    DEAD(4),
    GOOD(2),
    OVERHEAT(3),
    OVER_VOLTAGE(5),
    UNKNOWN(1),
    UNSPECIFIED_FAILURE(6);

    companion object {
        private val values = values()
        fun getByValue(value: Int) = values.first { it.value == value }
    }
}

enum class BatteryPowerSourceType(val value: Int) {
    NONE(0),
    AC(1),
    USB(2),
    WIRELESS(4);

    companion object {
        private val values = values()
        fun getByValue(value: Int) = values.first { it.value == value }
    }
}

enum class BatteryStatusType(val value: Int) {
    CHARGING(2),
    DISCHARGING(3),
    FULL(5),
    NOT_CHARGING(4),
    UNKNOWN(1);

    companion object {
        private val values = values()
        fun getByValue(value: Int) = values.first { it.value == value }
    }
}