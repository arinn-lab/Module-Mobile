package com.example.wheelsup.ui.screen.vehicle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wheelsup.ui.viewmodel.MotorViewModel
import com.example.wheelsup.ui.viewmodel.ViewModelFactory
import androidx.compose.runtime.saveable.rememberSaveable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditVehicleScreen(
    navController: NavController,
    motorId: Int,
    viewModelFactory: ViewModelFactory
) {
    val motorViewModel: MotorViewModel = viewModel(factory = viewModelFactory)
    val selectedMotor by motorViewModel.selectedMotor.collectAsState()

    var nickname by rememberSaveable { mutableStateOf("") }
    var brand by rememberSaveable { mutableStateOf("") }
    var model by rememberSaveable { mutableStateOf("") }
    var ccText by rememberSaveable { mutableStateOf("") }
    var compressionText by rememberSaveable { mutableStateOf("") }
    var odometerText by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var isInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(motorId) {
        motorViewModel.loadMotorById(motorId)
    }

    LaunchedEffect(selectedMotor) {
        if (!isInitialized && selectedMotor != null) {
            val motor = selectedMotor!!
            nickname = motor.nickname
            brand = motor.brand
            model = motor.model
            ccText = motor.cc.toString()
            compressionText = motor.compressionRatio.toString()
            odometerText = motor.lastOdometer.toString()
            isInitialized = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Motor") },
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
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Identitas Motor",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = nickname,
                onValueChange = { nickname = it },
                label = { Text("Nama Panggilan Motor") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = brand,
                    onValueChange = { brand = it },
                    label = { Text("Merk") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = model,
                    onValueChange = { model = it },
                    label = { Text("Model") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Spesifikasi Mesin",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = ccText,
                    onValueChange = { ccText = it },
                    label = { Text("CC") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = compressionText,
                    onValueChange = { compressionText = it },
                    label = { Text("Rasio Kompresi") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = odometerText,
                onValueChange = { odometerText = it },
                label = { Text("Kilometer Saat Ini") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val motor = selectedMotor ?: return@Button
                    val cc = ccText.toIntOrNull() ?: 0
                    val compression = compressionText.toDoubleOrNull() ?: 0.0
                    val odometer = odometerText.toIntOrNull() ?: 0

                    when {
                        nickname.isBlank() -> errorMessage = "Nama panggilan motor tidak boleh kosong"
                        brand.isBlank() -> errorMessage = "Merk tidak boleh kosong"
                        model.isBlank() -> errorMessage = "Model tidak boleh kosong"
                        cc <= 0 -> errorMessage = "CC harus diisi dengan angka yang valid"
                        compression <= 0.0 -> errorMessage = "Rasio kompresi harus diisi dengan angka yang valid"
                        else -> {
                            errorMessage = ""
                            motorViewModel.updateMotor(
                                motor.copy(
                                    nickname = nickname,
                                    brand = brand,
                                    model = model,
                                    cc = cc,
                                    compressionRatio = compression,
                                    lastOdometer = odometer
                                )
                            )
                            navController.navigateUp()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan Perubahan")
            }

            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}