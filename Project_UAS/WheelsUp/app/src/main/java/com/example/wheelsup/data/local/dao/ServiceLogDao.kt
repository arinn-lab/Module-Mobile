package com.example.wheelsup.data.local.dao

import androidx.room.*
import com.example.wheelsup.data.local.entity.LogServisEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LogServisDao {

    @Query("SELECT * FROM log_servis WHERE motorId = :motorId ORDER BY tanggal DESC")
    fun getLogServisByMotorId(motorId: Int): Flow<List<LogServisEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLogServis(logServis: LogServisEntity)

    @Update
    suspend fun updateLogServis(logServis: LogServisEntity)

    @Delete
    suspend fun deleteLogServis(logServis: LogServisEntity)
}