package com.example.wheelsup.data.repository

import com.example.wheelsup.data.local.dao.MotorDao
import com.example.wheelsup.data.local.entity.MotorEntity
import com.example.wheelsup.domain.model.Motor
import com.example.wheelsup.domain.repository.MotorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MotorRepositoryImpl(
    private val motorDao: MotorDao
) : MotorRepository {

    override fun getAllMotors(userId: String): Flow<List<Motor>> {
        return motorDao.getAllMotors(userId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getMotorById(motorId: Int): Flow<Motor?> {
        return motorDao.getMotorById(motorId).map { it?.toDomain() }
    }

    override suspend fun insertMotor(motor: Motor) {
        motorDao.insertMotor(motor.toEntity())
    }

    override suspend fun updateMotor(motor: Motor) {
        motorDao.updateMotor(motor.toEntity())
    }

    override suspend fun deleteMotor(motor: Motor) {
        motorDao.deleteMotor(motor.toEntity())
    }

    private fun MotorEntity.toDomain() = Motor(
        id = id,
        userId = userId,
        nickname = nickname,
        brand = brand,
        model = model,
        cc = cc,
        compressionRatio = compressionRatio,
        maintenanceCategory = maintenanceCategory,
        lastOdometer = lastOdometer,
        serviceIntervalKm = serviceIntervalKm
    )

    private fun Motor.toEntity() = MotorEntity(
        id = id,
        userId = userId,
        nickname = nickname,
        brand = brand,
        model = model,
        cc = cc,
        compressionRatio = compressionRatio,
        maintenanceCategory = maintenanceCategory,
        lastOdometer = lastOdometer,
        serviceIntervalKm = serviceIntervalKm
    )
}