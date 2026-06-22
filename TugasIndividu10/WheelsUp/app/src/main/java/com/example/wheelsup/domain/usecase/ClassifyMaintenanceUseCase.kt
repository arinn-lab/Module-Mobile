package com.example.wheelsup.domain.usecase

class ClassifyMaintenanceUseCase {

    fun execute(cc: Int, compressionRatio: Double): String {
        return when {
            cc >= 250 && compressionRatio >= 11.0 -> "HIGH"
            cc < 250 && compressionRatio >= 11.0 -> "MEDIUM"
            cc >= 250 && compressionRatio < 11.0 -> "MEDIUM"
            else -> "LOW"
        }
    }

    fun getServiceIntervalKm(category: String): Int {
        return when (category) {
            "HIGH" -> 1500
            "MEDIUM" -> 2000
            else -> 2500
        }
    }

    fun getChecklist(category: String): List<String> {
        return when (category) {
            "HIGH" -> listOf(
                "Engine Oil",
                "Spark Plug (Iridium)",
                "Air Filter",
                "Chain/V-Belt",
                "Coolant",
                "Electrical & Battery"
            )
            "MEDIUM" -> listOf(
                "Engine Oil",
                "Spark Plug",
                "Air Filter",
                "Chain/V-Belt"
            )
            else -> listOf(
                "Engine Oil",
                "Spark Plug"
            )
        }
    }
}