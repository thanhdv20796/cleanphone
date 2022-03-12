package com.example.cleanphone.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.cleanphone.data.db.Converters
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*


@Entity(tableName = "BatteryStatus")
@TypeConverters(Converters::class)
data class BatteryStatus(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @field:SerializedName("date")
    val date: Date = Date(),

    @field:SerializedName("health")
    val health: BatteryHealthType,

    @field:SerializedName("currentLevel")
    val currentLevel: Int,

    @field:SerializedName("powerSource")
    val powerSource: BatteryPowerSourceType,

    @field:SerializedName("isPresent")
    val isPresent: Boolean,

    @field:SerializedName("maxLevel")
    val maxLevel: Int,

    @field:SerializedName("status")
    val status: BatteryStatusType,

    @field:SerializedName("technology")
    val technology: String,

    @field:SerializedName("temperature")
    val temperature: Int,

    @field:SerializedName("voltage_level")
    val voltageLevel: Int,

    @field:SerializedName("capacityRemainingPercentage")
    val capacityRemainingPercentage: Int,

    @field:SerializedName("capacity_in_microampere_hours")
    val capacityInMicroampereHours: Int,

    @field:SerializedName("average_microamperes")
    val averageMicroamperes: Int,

    @field:SerializedName("current_microamperes")
    val currentMicroamperes: Int,

    @field:SerializedName("remaining_energy_nanowattHour")
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

@TypeConverter
fun toDate(dateLong: Long?): Date? {
    return dateLong?.let { Date(it) }
}

@TypeConverter
fun fromDate(date: Date?): Long? {
    return date?.getTime()
}

@TypeConverter
fun fromIntToBatteryHealth(value: Int?): BatteryHealthType?{
    return if(value == null) null else BatteryHealthType.getByValue(value)
}

@TypeConverter
fun fromBatteryHealthToInt(value: BatteryHealthType?): Int?{
    return value?.value
}

@TypeConverter
fun fromIntToBatteryPowerSource(value: Int?): BatteryPowerSourceType?{
    return if(value == null) null else BatteryPowerSourceType.getByValue(value)
}

@TypeConverter
fun fromBatteryPowerSourceToInt(value: BatteryPowerSourceType?): Int?{
    return value?.value
}

@TypeConverter
fun fromIntToBatteryStatusType(value: Int?): BatteryStatusType?{
    return if(value == null) null else BatteryStatusType.getByValue(value)
}

@TypeConverter
fun fromBatteryStatusTypeToInt(value: BatteryStatusType?): Int?{
    return value?.value
}
