package com.example.cleanphone.ui.home.presentation

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import com.example.cleanphone.ui.base.BaseViewModel
import com.example.cleanphone.ui.battery.data.BatterySaverRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import android.app.ActivityManager
import android.widget.Toast

import android.app.ActivityManager.RunningAppProcessInfo
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
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


    fun clearAllAppRunBackground() {
        viewModelScope.launch {
            try {

            } catch (e: HttpException) {
            } catch (e: Exception) {
            }

        }

    }

}

