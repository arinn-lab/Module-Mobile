package com.example.wheelsup.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "log_servis",
    foreignKeys = [
        ForeignKey(
            entity = MotorEntity::class,
            parentColumns = ["id"],
            childColumns = ["motorId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LogServisEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val motorId: Int,
    val jenisServis: String,
    val biaya: Long,
    val kilometerSaatServis: Int,
    val tanggal: Long,
    val catatan: String,
    val komponenDicentang: String
)