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
import retrofit2.HttpException

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val repository: BatterySaverRepositoryImp
) : BaseViewModel() {
    private val removePackage = MutableLiveData<Resource<String>>()
    val listHistoryBatteryCharging: LiveData<Resource<String>> get() = removePackage


    fun getAllRam(): Long {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    fun clearAllAppRunBackground() {
        viewModelScope.launch {
            try {
                val am = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager?

                val pm = context.packageManager.getInstalledApplications(0)
                //get a list of installed apps.
                val addString: List<String>
                for (runningProcess in pm) {
                    if (runningProcess.flags and ApplicationInfo.FLAG_SYSTEM == 1) continue
                    if (runningProcess.packageName == context.packageName) continue
                    Log.v("Hello", "kill process " + runningProcess.uid)
                    Log.v("Hello", "kill process " + runningProcess.processName)
                    am?.killBackgroundProcesses(runningProcess.processName)
                    removePackage.postValue(Resource.success(runningProcess.processName))
                }
            } catch (e: HttpException) {
                removePackage.postValue(
                    Resource.error(
                        "Null Data",
                        null
                    )
                )
            } catch (e: Exception) {
                removePackage.postValue(
                    Resource.error(
                        e.localizedMessage ?: e.message!!,
                        null
                    )
                )
            }

        }


    }
}