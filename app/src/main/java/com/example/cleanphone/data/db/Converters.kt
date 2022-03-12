package com.example.cleanphone.data.db
import androidx.room.TypeConverter
import com.example.cleanphone.data.model.local.BatteryHealthType
import com.example.cleanphone.data.model.local.BatteryPowerSourceType
import com.example.cleanphone.data.model.local.BatteryStatusType
import java.util.*

class Converters {
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

}