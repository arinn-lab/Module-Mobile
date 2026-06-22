package com.example.wheelsup.ui.screen.service

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wheelsup.domain.usecase.ClassifyMaintenanceUseCase
import com.example.wheelsup.ui.viewmodel.MotorViewModel
import com.example.wheelsup.ui.viewmodel.ServiceLogViewModel
import com.example.wheelsup.ui.viewmodel.ViewModelFactory
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.mapSaver

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditServiceLogScreen(
    navController: NavController,
    motorId: Int,
    logId: Int,
    viewModelFactory: ViewModelFactory
) {
    val motorViewModel: MotorViewModel = viewModel(factory = viewModelFactory)
    val serviceLogViewModel: ServiceLogViewModel = viewModel(factory = viewModelFactory)
    val selectedMotor by motorViewModel.selectedMotor.collectAsState()
    val serviceLogs by serviceLogViewModel.serviceLogs.collectAsState()
    val classifyMaintenanceUseCase = remember { ClassifyMaintenanceUseCase() }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var isInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(motorId) {
        motorViewModel.loadMotorById(motorId)
        serviceLogViewModel.loadServiceLogs(motorId)
    }

    var serviceType by rememberSaveable { mutableStateOf("Ganti Oli") }
    var costText by rememberSaveable { mutableStateOf("") }
    var odometerText by rememberSaveable { mutableStateOf("") }
    var notes by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    val serviceTypeOptions = listOf("Ganti Oli", "Ganti Ban", "Ganti Aki", "Lainnya")
    val checklist = selectedMotor?.let {
        classifyMaintenanceUseCase.getChecklist(it.maintenanceCategory)
    } ?: emptyList()

    val checkedComponents = rememberSaveable(
        saver = mapSaver(
            save = { stateMap -> stateMap.toMap() },
            restore = { savedMap ->
                mutableStateMapOf<String, Boolean>().apply {
                    savedMap.forEach { (key, value) -> put(key, value as Boolean) }
                }
            }
        )
    ) { mutableStateMapOf<String, Boolean>() }
    var serviceTypeExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(serviceLogs) {
        if (!isInitialized) {
            val log = serviceLogs.find { it.id == logId }
            if (log != null) {
                serviceType = log.serviceType
                costText = log.cost.toString()
                odometerText = log.odometer.toString()
                notes = log.notes
                log.checkedComponents.forEach { checkedComponents[it] = true }
                isInitialized = true
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Log Servis") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Hapus Log")
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
            ExposedDropdownMenuBox(
                expanded = serviceTypeExpanded,
                onExpandedChange = { serviceTypeExpanded = it }
            ) {
                OutlinedTextField(
                    value = serviceType,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Jenis Servis") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = serviceTypeExpanded,
                    onDismissRequest = { serviceTypeExpanded = false }
                ) {
                    serviceTypeOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                serviceType = option
                                serviceTypeExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = costText,
                onValueChange = { costText = it },
                label = { Text("Biaya (Rp)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = odometerText,
                onValueChange = { odometerText = it },
                label = { Text("Kilometer Saat Servis") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Checklist Komponen",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            checklist.forEach { component ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checkedComponents[component] ?: false,
                        onCheckedChange = { checkedComponents[component] = it }
                    )
                    Text(text = component)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Catatan (opsional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val originalLog = serviceLogs.find { it.id == logId } ?: return@Button
                    val cost = costText.toLongOrNull() ?: 0L
                    val odometer = odometerText.toIntOrNull() ?: 0

                    when {
                        odometerText.isBlank() -> errorMessage = "Kilometer servis tidak boleh kosong"
                        odometer <= 0 -> errorMessage = "Kilometer servis harus lebih dari 0"
                        costText.isBlank() -> errorMessage = "Biaya tidak boleh kosong"
                        else -> {
                            errorMessage = ""
                            val selected = checkedComponents.filter { it.value }.keys.toList()
                            serviceLogViewModel.updateServiceLog(
                                originalLog.copy(
                                    serviceType = serviceType,
                                    cost = cost,
                                    odometer = odometer,
                                    notes = notes,
                                    checkedComponents = selected
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
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Hapus Log Servis?") },
            text = { Text("Tindakan ini tidak bisa dibatalkan.") },
            confirmButton = {
                TextButton(onClick = {
                    val log = serviceLogs.find { it.id == logId }
                    log?.let { serviceLogViewModel.deleteServiceLog(it) }
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