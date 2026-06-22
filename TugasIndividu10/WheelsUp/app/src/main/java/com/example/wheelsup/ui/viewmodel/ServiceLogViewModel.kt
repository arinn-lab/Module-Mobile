package com.example.wheelsup.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheelsup.domain.model.ServiceLog
import com.example.wheelsup.domain.repository.ServiceLogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ServiceLogViewModel(
    private val serviceLogRepository: ServiceLogRepository
) : ViewModel() {

    private val _serviceLogs = MutableStateFlow<List<ServiceLog>>(emptyList())
    val serviceLogs: StateFlow<List<ServiceLog>> = _serviceLogs.asStateFlow()

    private val _selectedServiceLog = MutableStateFlow<ServiceLog?>(null)
    val selectedServiceLog: StateFlow<ServiceLog?> = _selectedServiceLog.asStateFlow()

    fun loadServiceLogs(motorId: Int) {
        viewModelScope.launch {
            serviceLogRepository.getServiceLogsByMotorId(motorId).collect { list ->
                _serviceLogs.value = list
            }
        }
    }

    fun addServiceLog(
        motorId: Int,
        serviceType: String,
        cost: Long,
        odometer: Int,
        date: Long,
        notes: String,
        checkedComponents: List<String>
    ) {
        viewModelScope.launch {
            val serviceLog = ServiceLog(
                motorId = motorId,
                serviceType = serviceType,
                cost = cost,
                odometer = odometer,
                date = date,
                notes = notes,
                checkedComponents = checkedComponents
            )
            serviceLogRepository.insertServiceLog(serviceLog)
        }
    }

    fun updateServiceLog(serviceLog: ServiceLog) {
        viewModelScope.launch {
            serviceLogRepository.updateServiceLog(serviceLog)
        }
    }

    fun deleteServiceLog(serviceLog: ServiceLog) {
        viewModelScope.launch {
            serviceLogRepository.deleteServiceLog(serviceLog)
        }
    }

    fun selectServiceLog(serviceLog: ServiceLog) {
        _selectedServiceLog.value = serviceLog
    }
}