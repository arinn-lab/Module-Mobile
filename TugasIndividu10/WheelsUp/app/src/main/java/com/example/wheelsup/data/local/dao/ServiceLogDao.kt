package com.example.wheelsup.data.local.dao

import androidx.room.*
import com.example.wheelsup.data.local.entity.ServiceLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceLogDao {

    @Query("SELECT * FROM service_logs WHERE motorId = :motorId ORDER BY date DESC")
    fun getServiceLogsByMotorId(motorId: Int): Flow<List<ServiceLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServiceLog(serviceLog: ServiceLogEntity)

    @Update
    suspend fun updateServiceLog(serviceLog: ServiceLogEntity)

    @Delete
    suspend fun deleteServiceLog(serviceLog: ServiceLogEntity)
}