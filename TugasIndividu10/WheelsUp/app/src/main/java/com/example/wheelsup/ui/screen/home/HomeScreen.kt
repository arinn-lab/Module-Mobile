package com.example.wheelsup.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
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
import com.example.wheelsup.ui.navigation.Screen
import com.example.wheelsup.ui.viewmodel.MotorViewModel
import com.example.wheelsup.ui.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModelFactory: ViewModelFactory
) {
    val motorViewModel: MotorViewModel = viewModel(factory = viewModelFactory)
    val motors by motorViewModel.motors.collectAsState()
    val currentUser = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(currentUser) {
        currentUser?.uid?.let { motorViewModel.loadMotors(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Garasiku") },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Profile.route) }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddVehicle.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Motor")
            }
        }
    ) { paddingValues ->
        if (motors.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Belum ada motor",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tap tombol + untuk menambahkan motor pertamamu",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(motors) { motor ->
                    MotorCard(
                        motor = motor,
                        onClick = {
                            navController.navigate(Screen.VehicleDetail.createRoute(motor.id))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MotorCard(motor: Motor, onClick: () -> Unit) {
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
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = motor.nickname,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${motor.brand} ${motor.model}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${motor.lastOdometer} km",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Surface(
                color = badgeColor,
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = badgeText,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}