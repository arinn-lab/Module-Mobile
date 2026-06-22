package com.example.wheelsup.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wheelsup.ui.screen.auth.LoginScreen
import com.example.wheelsup.ui.screen.auth.RegisterScreen
import com.example.wheelsup.ui.screen.home.HomeScreen
import com.example.wheelsup.ui.screen.vehicle.AddVehicleScreen
import com.example.wheelsup.ui.screen.vehicle.EditVehicleScreen
import com.example.wheelsup.ui.screen.vehicle.VehicleDetailScreen
import com.example.wheelsup.ui.screen.service.AddServiceLogScreen
import com.example.wheelsup.ui.screen.service.EditServiceLogScreen
import com.example.wheelsup.ui.screen.profile.ProfileScreen
import com.example.wheelsup.ui.screen.profile.SettingsScreen
import com.example.wheelsup.ui.viewmodel.ViewModelFactory

private const val FADE_DURATION_MS = 180

@Composable
fun WheelsUpNavGraph(
    navController: NavHostController,
    startDestination: String,
    viewModelFactory: ViewModelFactory
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { fadeIn(animationSpec = tween(FADE_DURATION_MS)) },
        exitTransition = { fadeOut(animationSpec = tween(FADE_DURATION_MS)) },
        popEnterTransition = { fadeIn(animationSpec = tween(FADE_DURATION_MS)) },
        popExitTransition = { fadeOut(animationSpec = tween(FADE_DURATION_MS)) }
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                viewModelFactory = viewModelFactory
            )
        }
        composable(Screen.AddVehicle.route) {
            AddVehicleScreen(
                navController = navController,
                viewModelFactory = viewModelFactory
            )
        }
        composable(
            route = Screen.EditVehicle.route,
            arguments = listOf(navArgument("motorId") { type = NavType.IntType })
        ) { backStackEntry ->
            val motorId = backStackEntry.arguments?.getInt("motorId") ?: 0
            EditVehicleScreen(
                navController = navController,
                motorId = motorId,
                viewModelFactory = viewModelFactory
            )
        }
        composable(
            route = Screen.VehicleDetail.route,
            arguments = listOf(navArgument("motorId") { type = NavType.IntType })
        ) { backStackEntry ->
            val motorId = backStackEntry.arguments?.getInt("motorId") ?: 0
            VehicleDetailScreen(
                navController = navController,
                motorId = motorId,
                viewModelFactory = viewModelFactory
            )
        }
        composable(
            route = Screen.AddServiceLog.route,
            arguments = listOf(navArgument("motorId") { type = NavType.IntType })
        ) { backStackEntry ->
            val motorId = backStackEntry.arguments?.getInt("motorId") ?: 0
            AddServiceLogScreen(
                navController = navController,
                motorId = motorId,
                viewModelFactory = viewModelFactory
            )
        }
        composable(
            route = Screen.EditServiceLog.route,
            arguments = listOf(
                navArgument("motorId") { type = NavType.IntType },
                navArgument("logId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val motorId = backStackEntry.arguments?.getInt("motorId") ?: 0
            val logId = backStackEntry.arguments?.getInt("logId") ?: 0
            EditServiceLogScreen(
                navController = navController,
                motorId = motorId,
                logId = logId,
                viewModelFactory = viewModelFactory
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                navController = navController,
                viewModelFactory = viewModelFactory
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                navController = navController,
                viewModelFactory = viewModelFactory
            )
        }
    }
}