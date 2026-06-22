package com.example.wheelsup

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.wheelsup.data.local.database.WheelsUpDatabase
import com.example.wheelsup.notification.ServiceReminderWorker
import com.example.wheelsup.ui.navigation.WheelsUpNavGraph
import com.example.wheelsup.ui.theme.WheelsUpTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wheelsup.ui.viewmodel.SettingsViewModel
import com.example.wheelsup.ui.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.example.wheelsup.data.local.SettingsDataStore
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        requestNotificationPermissionIfNeeded()
        scheduleServiceReminderWorker()

        val database = Room.databaseBuilder(
            applicationContext,
            WheelsUpDatabase::class.java,
            "wheelsup_database"
        ).build()

        val settingsDataStore = SettingsDataStore(applicationContext)

        val viewModelFactory = ViewModelFactory(
            motorDao = database.motorDao(),
            serviceLogDao = database.serviceLogDao(),
            settingsDataStore = settingsDataStore,
            appContext = applicationContext
        )

        val currentUser = FirebaseAuth.getInstance().currentUser
        val startDestination = if (currentUser != null) "home" else "login"

        setContent {
            val settingsViewModel: SettingsViewModel = viewModel(factory = viewModelFactory)
            val isDarkTheme by settingsViewModel.isDarkTheme.collectAsState()

            WheelsUpTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                WheelsUpNavGraph(
                    navController = navController,
                    startDestination = startDestination,
                    viewModelFactory = viewModelFactory
                )
            }
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!granted) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun scheduleServiceReminderWorker() {
        val reminderRequest = PeriodicWorkRequestBuilder<ServiceReminderWorker>(
            12, TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "service_reminder_work",
            ExistingPeriodicWorkPolicy.KEEP,
            reminderRequest
        )
    }
}