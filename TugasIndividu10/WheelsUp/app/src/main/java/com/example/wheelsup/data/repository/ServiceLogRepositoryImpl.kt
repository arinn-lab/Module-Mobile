package com.example.wheelsup.data.repository

import com.example.wheelsup.data.local.dao.ServiceLogDao
import com.example.wheelsup.data.local.entity.ServiceLogEntity
import com.example.wheelsup.domain.model.ServiceLog
import com.example.wheelsup.domain.repository.ServiceLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ServiceLogRepositoryImpl(
    private val serviceLogDao: ServiceLogDao
) : ServiceLogRepository {

    override fun getServiceLogsByMotorId(motorId: Int): Flow<List<ServiceLog>> {
        return serviceLogDao.getServiceLogsByMotorId(motorId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun insertServiceLog(serviceLog: ServiceLog) {
        serviceLogDao.insertServiceLog(serviceLog.toEntity())
    }

    override suspend fun updateServiceLog(serviceLog: ServiceLog) {
        serviceLogDao.updateServiceLog(serviceLog.toEntity())
    }

    override suspend fun deleteServiceLog(serviceLog: ServiceLog) {
        serviceLogDao.deleteServiceLog(serviceLog.toEntity())
    }

    private fun ServiceLogEntity.toDomain() = ServiceLog(
        id = id,
        motorId = motorId,
        serviceType = serviceType,
        cost = cost,
        odometer = odometer,
        date = date,
        notes = notes,
        checkedComponents = checkedComponents.split(",").filter { it.isNotEmpty() }
    )

    private fun ServiceLog.toEntity() = ServiceLogEntity(
        id = id,
        motorId = motorId,
        serviceType = serviceType,
        cost = cost,
        odometer = odometer,
        date = date,
        notes = notes,
        checkedComponents = checkedComponents.joinToString(",")
    )
}