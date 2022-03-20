package com.example.cleanphone.ui.cleanram.data

import android.graphics.drawable.Drawable

data class RamUsageInfo(

    var iconApp: Drawable,
    var appName: String,
    var pName: String,
    var isCheckBox: Boolean,
    var versionName: String,
    var versionCode: Int,
) {
    var cache: ListCache? = null
    var pid = IntArray(0);
}


data class ListCache(
    var dalvikPrivateDirty: Int,
    var dalvikPss: Int,
    var dalvikSharedDirty: Int,
    var nativePrivateDirty: Int,
    var nativePss: Int,
    var nativeSharedDirty: Int,
    var otherPrivateDirty: Int,
    var otherPss: Int,
    var otherSharedDirty: Int,
)