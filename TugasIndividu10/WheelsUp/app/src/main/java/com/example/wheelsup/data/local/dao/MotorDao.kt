package com.example.wheelsup.data.local.dao

import androidx.room.*
import com.example.wheelsup.data.local.entity.MotorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MotorDao {

    @Query("SELECT * FROM motors WHERE userId = :userId ORDER BY id DESC")
    fun getAllMotors(userId: String): Flow<List<MotorEntity>>

    @Query("SELECT * FROM motors WHERE id = :motorId")
    fun getMotorById(motorId: Int): Flow<MotorEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMotor(motor: MotorEntity)

    @Update
    suspend fun updateMotor(motor: MotorEntity)

    @Delete
    suspend fun deleteMotor(motor: MotorEntity)
}