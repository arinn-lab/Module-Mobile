package com.example.wheelsup.ui.screen.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wheelsup.ui.viewmodel.SettingsViewModel
import com.example.wheelsup.ui.viewmodel.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModelFactory: ViewModelFactory
) {
    val settingsViewModel: SettingsViewModel = viewModel(factory = viewModelFactory)
    val isDarkTheme by settingsViewModel.isDarkTheme.collectAsState()
    val isNotificationEnabled by settingsViewModel.isNotificationEnabled.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pengaturan") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Tema Gelap")
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { settingsViewModel.setDarkTheme(it) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Notifikasi Pengingat Servis")
                Switch(
                    checked = isNotificationEnabled,
                    onCheckedChange = { settingsViewModel.setNotificationEnabled(it) }
                )
            }
        }
    }
}