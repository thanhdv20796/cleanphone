package com.example.cleanphone.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.graphics.drawable.Drawable
import android.nfc.Tag
import android.text.format.Formatter
import android.util.Log
import com.example.cleanphone.data.Resource
import com.example.cleanphone.ui.cleanram.data.ListCache
import com.example.cleanphone.ui.cleanram.data.RamUsageInfo
import com.example.cleanphone.ui.home.presentation.HomeViewModel
import java.util.*
import android.app.ActivityManager.RunningAppProcessInfo
import android.os.Process


class RamUtils(val context: Context) {
    private val am = (context.getSystemService(ACTIVITY_SERVICE) as ActivityManager)

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }

    fun readInfoRam(): String {
        val memoryInfo = ActivityManager.MemoryInfo()
        (context.getSystemService(ACTIVITY_SERVICE) as ActivityManager).getMemoryInfo(memoryInfo)
        val nativeHeapSize = memoryInfo.totalMem
        val nativeHeapFreeSize = memoryInfo.availMem
        val usedMemInBytes = nativeHeapSize - nativeHeapFreeSize
        val usedMemInPercentage = (usedMemInBytes * 100 / nativeHeapSize)
        return "total:${Formatter.formatFileSize(context, nativeHeapSize)} " +
                "free:${Formatter.formatFileSize(context, nativeHeapFreeSize)} " +
                "used:${
                    Formatter.formatFileSize(
                        context,
                        usedMemInBytes
                    )
                } ($usedMemInPercentage%)"
    }

    fun killAppBackground() {

        val packs: List<PackageInfo> = context.packageManager.getInstalledPackages(0)
        for (i in packs.indices) {
            if (packs[i].applicationInfo.flags == 1) continue
            if (packs[i].packageName == context.packageName) continue
            Log.v("Hello", "kill process " + packs[i].packageName)
            am.killBackgroundProcesses(packs[i].packageName)
        }

    }


    fun getPackages(): List<RamUsageInfo> {
        return getInstalledApps(false)
    }


    private fun getInstalledApps(getSysPackages: Boolean): ArrayList<RamUsageInfo> {
        val res = ArrayList<RamUsageInfo>()
        val packs: List<PackageInfo> = context.packageManager.getInstalledPackages(0)
        for (i in packs.indices) {
            val p = packs[i]
            if (!getSysPackages && p.versionName == null) {
                continue
            }
            val newInfo = RamUsageInfo(
                appName = p.applicationInfo.loadLabel(context.packageManager).toString(),
                iconApp = p.applicationInfo.loadIcon(context.packageManager),
                isCheckBox = true,
                pName = p.applicationInfo.uid.toString(),
                versionCode = p.versionCode,
                versionName = p.versionName
            )

            val processInfo = am.runningAppProcesses

            val pids = IntArray(processInfo.size)
            for (i in processInfo.indices) {
                val info = processInfo[i]
                pids[i] = info.pid
            }

            val procsMemInfo = am.getProcessMemoryInfo(pids)
            for (info in procsMemInfo) {
                newInfo.cache = ListCache(
                    nativePrivateDirty = info.nativePrivateDirty,
                    nativeSharedDirty = info.nativeSharedDirty,
                    nativePss = info.nativePss,
                    dalvikPrivateDirty = info.dalvikPrivateDirty,
                    dalvikPss = info.dalvikPss,
                    dalvikSharedDirty = info.dalvikSharedDirty,
                    otherPrivateDirty = info.otherPrivateDirty,
                    otherPss = info.otherPss,
                    otherSharedDirty = info.otherSharedDirty,
                )
            }
            res.add(newInfo)
        }
        return res
    }

}