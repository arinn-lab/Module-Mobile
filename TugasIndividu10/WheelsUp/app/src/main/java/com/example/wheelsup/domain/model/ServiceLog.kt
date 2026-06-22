package com.example.wheelsup.domain.model

data class ServiceLog(
    val id: Int = 0,
    val motorId: Int,
    val serviceType: String,
    val cost: Long,
    val odometer: Int,
    val date: Long,
    val notes: String,
    val checkedComponents: List<String>
)