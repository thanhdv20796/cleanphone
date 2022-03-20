package com.example.cleanphone.ui.cleanram.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cleanphone.data.Resource
import com.example.cleanphone.data.model.local.BatteryStatus
import com.example.cleanphone.ui.base.BaseViewModel
import com.example.cleanphone.ui.battery.data.BatterySaverRepositoryImp
import com.example.cleanphone.ui.cleanram.data.RamUsageInfo
import com.example.cleanphone.utils.RamUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class CleanRamViewModel @Inject constructor(
) : BaseViewModel() {
    private val ramUsageInfoEvent = MutableLiveData<Resource<List<RamUsageInfo>>>()
    val ramUsageInfoData: LiveData<Resource<List<RamUsageInfo>>> get() = ramUsageInfoEvent


    fun getAllListApplicationRun(context: Context) {
        viewModelScope.launch {
            try {
                ramUsageInfoEvent.postValue(
                    Resource.success(
                        RamUtils(context).getPackages()
                    )
                )
            } catch (e: Exception) {
                ramUsageInfoEvent.postValue(
                    null
                )
            }
        }
    }

    fun clearRam(context: Context) {
        RamUtils(context).killAppBackground()
    }

}
