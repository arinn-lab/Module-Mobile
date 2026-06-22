package com.example.tugasindividu9

import retrofit2.http.GET

interface ApiService {
    @GET("todos")
    suspend fun getData(): ApiResponse
}