package com.example.wheelsup.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object AddVehicle : Screen("add_vehicle")
    object EditVehicle : Screen("edit_vehicle/{motorId}") {
        fun createRoute(motorId: Int) = "edit_vehicle/$motorId"
    }
    object VehicleDetail : Screen("vehicle_detail/{motorId}") {
        fun createRoute(motorId: Int) = "vehicle_detail/$motorId"
    }
    object AddServiceLog : Screen("add_service_log/{motorId}") {
        fun createRoute(motorId: Int) = "add_service_log/$motorId"
    }
    object EditServiceLog : Screen("edit_service_log/{motorId}/{logId}") {
        fun createRoute(motorId: Int, logId: Int) = "edit_service_log/$motorId/$logId"
    }
    object Profile : Screen("profile")
    object Settings : Screen("settings")
}