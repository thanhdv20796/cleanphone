package com.example.cleanphone.ui.home.presentation

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.pm.ApplicationInfo
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cleanphone.data.Resource
import com.example.cleanphone.ui.base.BaseViewModel
import com.example.cleanphone.ui.battery.data.BatterySaverRepositoryImp
import com.example.cleanphone.ui.battery.data.CpuInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.content.Context.ACTIVITY_SERVICE

import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cleanphone.data.Resource
import com.example.cleanphone.data.model.local.BatteryStatus
import com.example.cleanphone.lib.BatteryUsageWorker
import retrofit2.HttpException
import java.io.*
import java.util.regex.Pattern
import javax.inject.Inject
import android.os.SystemClock

import android.content.Context.ACTIVITY_SERVICE
import android.os.Debug
import android.os.Process

import androidx.core.content.ContextCompat.getSystemService
import android.content.Context.ACTIVITY_SERVICE
import android.text.format.Formatter

import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemService
import android.content.Context.ACTIVITY_SERVICE
import java.util.*
import android.graphics.drawable.Drawable
import android.content.pm.PackageInfo
import java.io.File
import android.content.Context.ACTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import com.example.cleanphone.utils.RamUtils


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: BatterySaverRepositoryImp
) : BaseViewModel() {
    private val removePackage = MutableLiveData<Resource<String>>()
    val listHistoryBatteryCharging: LiveData<Resource<String>> get() = removePackage

    //public val CPU_INFO_DIR = "/sys/devices/system/cpu/"

    fun getAvailableMemory(): Long {
        //return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        //return Runtime.getRuntime().freeMemory();
        val actManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        actManager.getMemoryInfo(memInfo)
        val totalMemory = memInfo.totalMem

        val availMemory = memInfo.availMem

        val usedMemory = totalMemory-availMemory

        return availMemory
    }

    fun getTotalMemory(): Long {
        val actManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        actManager.getMemoryInfo(memInfo)
        val totalMemory = memInfo.totalMem

        return totalMemory
    }

    fun getUsageMemory(): Long{
        val actManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        actManager.getMemoryInfo(memInfo)
        val totalMemory = memInfo.totalMem

        val availMemory = memInfo.availMem

        val usedMemory = totalMemory-availMemory

        return usedMemory
    }

    fun clearAllAppRunBackground() {
        viewModelScope.launch {
            try {

                val pm = context.packageManager.getInstalledApplications(0)
                //get a list of installed apps.
                val addString: List<String>
                for (runningProcess in pm) {
                    if (runningProcess.flags and ApplicationInfo.FLAG_SYSTEM == 1) continue
                    if (runningProcess.packageName == context.packageName) continue
                    Log.v("Hello", "kill process " + runningProcess.uid)
                    Log.v("Hello", "kill process " + runningProcess.processName)
                    Log.v("Free memory ", "-> " + Runtime.getRuntime().freeMemory());
                    am?.killBackgroundProcesses(runningProcess.processName)
                    removePackage.postValue(Resource.success(runningProcess.processName))
                }
            } catch (e: HttpException) {
            } catch (e: Exception) {
            }
        }
    }

    fun getMinMaxFreq(coreNumber: Int): Pair<Long, Long> {
        Log.d("Debug ", "Get freq cpu function: core " + coreNumber)
        val minPath = "${CpuInfoRepository.CPU_INFO_DIR}cpu$coreNumber/cpufreq/cpuinfo_min_freq"
        val maxPath = "${CpuInfoRepository.CPU_INFO_DIR}cpu$coreNumber/cpufreq/cpuinfo_max_freq"
        Log.d("Debug ", "Path " + minPath)
        return try {
            val minMhz = RandomAccessFile(minPath, "r").use { it.readLine().toLong() / 1000 }
            val maxMhz = RandomAccessFile(maxPath, "r").use { it.readLine().toLong() / 1000 }
            Log.v("Cpu", "Min freq: " + minMhz)
            Log.v("Cpu", "Max freq: " + maxMhz)
            Pair(minMhz, maxMhz)
        } catch (e: Exception) {
            Log.d("Debug", "Exception")
            Pair(-1, -1)
        }
    }

    fun getNumberOfCores(): Int {
        return if (Build.VERSION.SDK_INT >= 17) {
            Runtime.getRuntime().availableProcessors()
        } else {
            getNumCoresLegacy()
        }
    }

    private fun getNumCoresLegacy(): Int {
        class CpuFilter : FileFilter {
            override fun accept(pathname: File): Boolean {
                // Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]+", pathname.name)) {
                    return true
                }
                return false
            }
        }
        return try {
            File(CpuInfoRepository.CPU_INFO_DIR).listFiles(CpuFilter())!!.size
        } catch (e: Exception) {
            1
        }
    }

    fun getCurrentFreq(coreNumber: Int): Long {
        val currentFreqPath = "${CpuInfoRepository.CPU_INFO_DIR}cpu$coreNumber/cpufreq/scaling_cur_freq"
        return try {
            RandomAccessFile(currentFreqPath, "r").use { it.readLine().toLong() / 1000 }
        } catch (e: Exception) {
            -1
        }
    }

    fun cpuTemperature(): Float {
        val process: Process
        return try {
            process = Runtime.getRuntime().exec("cat sys/class/thermal/thermal_zone0/temp")
            process.waitFor()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val line = reader.readLine()
            if (line != null) {
                val temp = line.toFloat()
                Log.v("Debug ", "temp: " + temp)
                temp / 1000.0f
            } else {
                51.0f
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            0.0f
        }
    }

    fun thermal() {
        var temp: String?
        var type: String?
        for (i in 0..28) {
            temp = thermalTemp(i)
            Log.d("Debug ", "temp: ${temp}")
            if (!temp!!.contains("0.0")) {
                type = thermalType(i)

                if (type != null) {
                    Log.v("Debug ", "type->${type}, temp->${temp}")
                }
            }
        }
    }

    private fun thermalTemp(i: Int): String? {
        val process: Process
        val reader: BufferedReader
        val line: String?
        var t: String? = null
        var temp = 0f
        try {
//            process = Runtime.getRuntime().exec("cat sys/class/thermal/thermal_zone${i}/temp")
//            process.waitFor()
            var reader = RandomAccessFile("/sys/class/thermal/thermal_zone${i}/temp", "r")
            var line = reader.readLine()
            //reader = BufferedReader(InputStreamReader(process.inputStream))
            line = reader.readLine()
            if (line != null) {
                temp = line.toFloat()
            }
            reader.close()
            //process.destroy()
            if (temp.toInt() != 0) {
                if (temp.toInt() > 10000) {
                    temp = temp / 1000
                } else if (temp.toInt() > 1000) {
                    temp = temp / 100
                } else if (temp.toInt() > 100) {
                    temp = temp / 10
                }
            } else t = "0.0"
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return t
    }

    private fun thermalType(i: Int): String? {
        val process: Process
        val reader: BufferedReader
        val line: String?
        var type: String? = null
        try {
//            process = Runtime.getRuntime().exec("cat sys/class/thermal/thermal_zone${i}/type")
//            process.waitFor()
//            reader = BufferedReader(InputStreamReader(process.inputStream))
            var reader = RandomAccessFile("/sys/class/thermal/thermal_zone${i}/temp", "r")
            var line = reader.readLine()
            line = reader.readLine()
            if (line != null) {
                type = line
            }
            reader.close()
            //process.destroy()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return type
    }

}

