package com.example.wheelsup.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "motors")
data class MotorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val nickname: String,
    val brand: String,
    val model: String,
    val cc: Int,
    val compressionRatio: Double,
    val maintenanceCategory: String,
    val lastOdometer: Int,
    val serviceIntervalKm: Int
)