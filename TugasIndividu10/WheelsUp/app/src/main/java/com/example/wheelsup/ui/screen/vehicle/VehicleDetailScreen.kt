package com.example.wheelsup.ui.screen.vehicle

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wheelsup.domain.model.Motor
import com.example.wheelsup.domain.model.ServiceLog
import com.example.wheelsup.ui.navigation.Screen
import com.example.wheelsup.ui.viewmodel.MotorViewModel
import com.example.wheelsup.ui.viewmodel.ServiceLogViewModel
import com.example.wheelsup.ui.viewmodel.ViewModelFactory
import androidx.compose.material.icons.filled.Add

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleDetailScreen(
    navController: NavController,
    motorId: Int,
    viewModelFactory: ViewModelFactory
) {
    val motorViewModel: MotorViewModel = viewModel(factory = viewModelFactory)
    val serviceLogViewModel: ServiceLogViewModel = viewModel(factory = viewModelFactory)

    val selectedMotor by motorViewModel.selectedMotor.collectAsState()
    val serviceLogs by serviceLogViewModel.serviceLogs.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(motorId) {
        motorViewModel.loadMotorById(motorId)
        serviceLogViewModel.loadServiceLogs(motorId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(selectedMotor?.nickname ?: "Detail Motor") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.EditVehicle.createRoute(motorId))
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Motor")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Hapus Motor")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddServiceLog.createRoute(motorId))
            }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Log Servis")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            selectedMotor?.let { motor ->
                MotorSpecCard(motor = motor)
            }

            Text(
                text = "Riwayat Servis",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            if (serviceLogs.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Belum ada riwayat servis",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(serviceLogs) { log ->
                        ServiceLogCard(
                            serviceLog = log,
                            onClick = {
                                navController.navigate(
                                    Screen.EditServiceLog.createRoute(motorId, log.id)
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Hapus Motor?") },
            text = { Text("Semua riwayat servis motor ini juga akan ikut terhapus. Tindakan ini tidak bisa dibatalkan.") },
            confirmButton = {
                TextButton(onClick = {
                    selectedMotor?.let { motorViewModel.deleteMotor(it) }
                    showDeleteDialog = false
                    navController.navigateUp()
                }) {
                    Text("Hapus", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
fun MotorSpecCard(motor: Motor) {
    val badgeColor = when (motor.maintenanceCategory) {
        "HIGH" -> Color(0xFFE53935)
        "MEDIUM" -> Color(0xFFFB8C00)
        else -> Color(0xFF43A047)
    }
    val badgeText = when (motor.maintenanceCategory) {
        "HIGH" -> "High Maintenance"
        "MEDIUM" -> "Medium Maintenance"
        else -> "Low Maintenance"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${motor.brand} ${motor.model}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(color = badgeColor, shape = MaterialTheme.shapes.small) {
                Text(
                    text = badgeText,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "CC: ${motor.cc}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Kompresi: ${motor.compressionRatio}:1", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Odometer terakhir: ${motor.lastOdometer} km",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun ServiceLogCard(serviceLog: ServiceLog, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = serviceLog.serviceType,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "${serviceLog.odometer} km", style = MaterialTheme.typography.bodySmall)
                Text(text = "Rp ${serviceLog.cost}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}