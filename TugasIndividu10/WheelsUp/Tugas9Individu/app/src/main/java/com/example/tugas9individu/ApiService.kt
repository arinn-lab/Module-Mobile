package com.example.tugas9individu

import retrofit2.http.GET

interface ApiService {
    @GET("todos")
    suspend fun getData(): ApiResponse
}