package com.example.wheelsup.domain.model

data class Motor(
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