package com.example.wheelsup.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.wheelsup.data.local.SettingsDataStore
import com.example.wheelsup.notification.ServiceReminderWorker
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

private const val SERVICE_REMINDER_WORK_NAME = "service_reminder_work"

class SettingsViewModel(
    private val settingsDataStore: SettingsDataStore,
    private val appContext: Context
) : ViewModel() {

    val isDarkTheme: StateFlow<Boolean> = settingsDataStore.isDarkTheme.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    val isNotificationEnabled: StateFlow<Boolean> = settingsDataStore.isNotificationEnabled.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    fun setDarkTheme(enabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.setDarkTheme(enabled)
        }
    }

    fun setNotificationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.setNotificationEnabled(enabled)
        }

        val workManager = WorkManager.getInstance(appContext)
        if (enabled) {
            val reminderRequest = PeriodicWorkRequestBuilder<ServiceReminderWorker>(
                12, TimeUnit.HOURS
            ).build()
            workManager.enqueueUniquePeriodicWork(
                SERVICE_REMINDER_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                reminderRequest
            )
        } else {
            workManager.cancelUniqueWork(SERVICE_REMINDER_WORK_NAME)
        }
    }
}