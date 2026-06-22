package com.example.wheelsup.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MotorcycleResponse(
    @SerialName("make") val make: String = "",
    @SerialName("model") val model: String = "",
    @SerialName("year") val year: String = "",
    @SerialName("type") val type: String = "",
    @SerialName("displacement") val displacement: String = "",
    @SerialName("compression") val compression: String = "",
    @SerialName("power") val power: String = "",
    @SerialName("cooling") val cooling: String = ""
)