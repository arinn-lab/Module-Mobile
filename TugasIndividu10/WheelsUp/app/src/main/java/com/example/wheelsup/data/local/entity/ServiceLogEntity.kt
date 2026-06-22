package com.example.wheelsup.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "service_logs",
    foreignKeys = [
        ForeignKey(
            entity = MotorEntity::class,
            parentColumns = ["id"],
            childColumns = ["motorId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ServiceLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val motorId: Int,
    val serviceType: String,
    val cost: Long,
    val odometer: Int,
    val date: Long,
    val notes: String,
    val checkedComponents: String
)