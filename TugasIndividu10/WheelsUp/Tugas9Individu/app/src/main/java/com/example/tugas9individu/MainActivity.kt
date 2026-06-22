package com.example.tugas9individu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.tugas9individu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchData()
    }

    private fun fetchData() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getData()
                val sb = StringBuilder()
                sb.appendLine("Message: ${response.message}")
                sb.appendLine("Code: ${response.code}")
                sb.appendLine()
                response.data.forEach { item ->
                    sb.appendLine("ID: ${item.id}")
                    sb.appendLine("Name: ${item.name}")
                    sb.appendLine("Info: ${item.info}")
                    sb.appendLine("----------")
                }
                binding.tvResult.text = sb.toString()
            } catch (e: Exception) {
                binding.tvResult.text = "Error: ${e.message}"
            }
        }
    }
}