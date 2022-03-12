package com.example.cleanphone.ui.battery.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanphone.data.Resource
import com.example.cleanphone.data.db.BatteryStatusDao
import com.example.cleanphone.data.db.DBHelper
import com.example.cleanphone.domain.model.BatteryStatus
import com.example.cleanphone.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class BatterySaverViewModel @Inject constructor(
    private val repository: DBHelper
) : BaseViewModel() {
    private  val batteryStatusEvent = MutableLiveData<Resource<List<BatteryStatus>>>()
    val listHistoryBatteryCharging: LiveData<Resource<List<BatteryStatus>>> get() = batteryStatusEvent


    fun loadAllHistoryCharging(){
        batteryStatusEvent.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                batteryStatusEvent.postValue(Resource.success(repository.getAllHistoryBattery()))
            } catch (e: HttpException) {
                batteryStatusEvent.postValue(Resource.error(errorManager.getHttpError(e).description, null))
            } catch (e: Exception) {
                batteryStatusEvent.postValue(Resource.error(e.localizedMessage ?: e.message!!, null))
            }

        }
    }
}