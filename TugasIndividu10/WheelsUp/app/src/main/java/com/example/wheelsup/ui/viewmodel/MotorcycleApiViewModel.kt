package com.example.wheelsup.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheelsup.data.remote.api.ApiConfig
import com.example.wheelsup.data.remote.api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class SpecLookupState {
    object Idle : SpecLookupState()
    object Loading : SpecLookupState()
    data class Found(val cc: Int, val compressionRatio: Double) : SpecLookupState()
    object NotFound : SpecLookupState()
    data class Error(val message: String) : SpecLookupState()
}

class MotorcycleApiViewModel : ViewModel() {

    private val _specLookupState = MutableStateFlow<SpecLookupState>(SpecLookupState.Idle)
    val specLookupState: StateFlow<SpecLookupState> = _specLookupState.asStateFlow()

    fun fetchSpecs(brand: String, model: String) {
        viewModelScope.launch {
            _specLookupState.value = SpecLookupState.Loading
            try {
                val result = RetrofitInstance.api.getMotorcycles(
                    apiKey = ApiConfig.API_KEY,
                    make = brand,
                    model = model
                )
                if (result.isEmpty()) {
                    _specLookupState.value = SpecLookupState.NotFound
                } else {
                    val motorcycle = result.first()
                    val cc = parseDisplacement(motorcycle.displacement)
                    val compressionRatio = parseCompression(motorcycle.compression)
                    if (cc <= 0 || compressionRatio <= 0.0) {
                        _specLookupState.value = SpecLookupState.NotFound
                    } else {
                        _specLookupState.value = SpecLookupState.Found(cc, compressionRatio)
                    }
                }
            } catch (e: Exception) {
                _specLookupState.value = SpecLookupState.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }

    fun resetState() {
        _specLookupState.value = SpecLookupState.Idle
    }

    private fun parseCompression(raw: String): Double {
        val cleaned = raw.substringBefore(":").trim()
        return cleaned.toDoubleOrNull() ?: 0.0
    }

    private fun parseDisplacement(raw: String): Int {
        val numberPart = raw.substringBefore("ccm").trim()
        return numberPart.toDoubleOrNull()?.toInt() ?: 0
    }
}