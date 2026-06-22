package com.example.tugasindividu9

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val id: Int,
    val name: String,
    val info: String
)

@Serializable
data class ApiResponse(
    val message: String,
    val code: String,
    val data: List<Item>
)