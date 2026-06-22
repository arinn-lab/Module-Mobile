package com.example.wheelsup.domain.repository

import com.example.wheelsup.domain.model.ServiceLog
import kotlinx.coroutines.flow.Flow

interface ServiceLogRepository {
    fun getServiceLogsByMotorId(motorId: Int): Flow<List<ServiceLog>>
    suspend fun insertServiceLog(serviceLog: ServiceLog)
    suspend fun updateServiceLog(serviceLog: ServiceLog)
    suspend fun deleteServiceLog(serviceLog: ServiceLog)
}