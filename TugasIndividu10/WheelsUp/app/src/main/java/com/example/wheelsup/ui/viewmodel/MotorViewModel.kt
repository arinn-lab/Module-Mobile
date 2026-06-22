package com.example.wheelsup.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheelsup.domain.model.Motor
import com.example.wheelsup.domain.repository.MotorRepository
import com.example.wheelsup.domain.usecase.ClassifyMaintenanceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MotorViewModel(
    private val motorRepository: MotorRepository,
    private val classifyMaintenanceUseCase: ClassifyMaintenanceUseCase
) : ViewModel() {

    private val _motors = MutableStateFlow<List<Motor>>(emptyList())
    val motors: StateFlow<List<Motor>> = _motors.asStateFlow()

    private val _selectedMotor = MutableStateFlow<Motor?>(null)
    val selectedMotor: StateFlow<Motor?> = _selectedMotor.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    fun loadMotors(userId: String) {
        viewModelScope.launch {
            motorRepository.getAllMotors(userId).collect { list ->
                _motors.value = list
            }
        }
    }

    fun loadMotorById(motorId: Int) {
        viewModelScope.launch {
            motorRepository.getMotorById(motorId).collect { motor ->
                _selectedMotor.value = motor
            }
        }
    }

    fun addMotor(
        userId: String,
        nickname: String,
        brand: String,
        model: String,
        cc: Int,
        compressionRatio: Double,
        lastOdometer: Int
    ) {
        viewModelScope.launch {
            val category = classifyMaintenanceUseCase.execute(cc, compressionRatio)
            val intervalKm = classifyMaintenanceUseCase.getServiceIntervalKm(category)
            val motor = Motor(
                userId = userId,
                nickname = nickname,
                brand = brand,
                model = model,
                cc = cc,
                compressionRatio = compressionRatio,
                maintenanceCategory = category,
                lastOdometer = lastOdometer,
                serviceIntervalKm = intervalKm
            )
            motorRepository.insertMotor(motor)
        }
    }

    fun updateMotor(motor: Motor) {3
        viewModelScope.launch {
            val category = classifyMaintenanceUseCase.execute(motor.cc, motor.compressionRatio)
            val intervalKm = classifyMaintenanceUseCase.getServiceIntervalKm(category)
            motorRepository.updateMotor(
                motor.copy(
                    maintenanceCategory = category,
                    serviceIntervalKm = intervalKm
                )
            )
        }
    }

    fun deleteMotor(motor: Motor) {
        viewModelScope.launch {
            motorRepository.deleteMotor(motor)
        }
    }
}