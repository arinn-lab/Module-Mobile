package com.example.wheelsup.data.remote.api

import com.example.wheelsup.data.remote.model.MotorcycleResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MotorcycleApiService {

    @GET("v1/motorcycles")
    suspend fun getMotorcycles(
        @Header("X-Api-Key") apiKey: String,
        @Query("make") make: String,
        @Query("model") model: String
    ): List<MotorcycleResponse>
}