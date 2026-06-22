package com.example.wheelsup.domain.repository

import com.example.wheelsup.domain.model.Motor
import kotlinx.coroutines.flow.Flow

interface MotorRepository {
    fun getAllMotors(userId: String): Flow<List<Motor>>
    fun getMotorById(motorId: Int): Flow<Motor?>
    suspend fun insertMotor(motor: Motor)
    suspend fun updateMotor(motor: Motor)
    suspend fun deleteMotor(motor: Motor)
}