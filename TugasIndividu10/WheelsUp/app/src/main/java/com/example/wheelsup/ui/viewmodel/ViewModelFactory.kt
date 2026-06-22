package com.example.wheelsup.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wheelsup.data.local.SettingsDataStore
import com.example.wheelsup.data.local.dao.MotorDao
import com.example.wheelsup.data.local.dao.ServiceLogDao
import com.example.wheelsup.data.repository.MotorRepositoryImpl
import com.example.wheelsup.data.repository.ServiceLogRepositoryImpl
import com.example.wheelsup.domain.usecase.ClassifyMaintenanceUseCase

class ViewModelFactory(
    private val motorDao: MotorDao,
    private val serviceLogDao: ServiceLogDao,
    private val settingsDataStore: SettingsDataStore,
    private val appContext: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val motorRepository = MotorRepositoryImpl(motorDao)
        val serviceLogRepository = ServiceLogRepositoryImpl(serviceLogDao)
        val classifyMaintenanceUseCase = ClassifyMaintenanceUseCase()

        return when {
            modelClass.isAssignableFrom(MotorViewModel::class.java) -> {
                MotorViewModel(motorRepository, classifyMaintenanceUseCase) as T
            }
            modelClass.isAssignableFrom(ServiceLogViewModel::class.java) -> {
                ServiceLogViewModel(serviceLogRepository) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(settingsDataStore, appContext) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}