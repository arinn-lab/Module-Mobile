package com.example.songpediacompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.songpediacompose.viewmodel.SongViewModel
import com.example.songpediacompose.viewmodel.SongViewModelFactory

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val viewModel: SongViewModel = viewModel(
        factory = SongViewModelFactory(context.applicationContext as android.app.Application, "local")
    )

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onDetailClick = { song ->
                    viewModel.selectSong(song)
                    navController.navigate("detail")
                },
                onLanguageClick = {
                    navController.navigate("language")
                }
            )
        }
        composable("detail") {
            DetailScreen(
                viewModel = viewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable("language") {
            LanguageScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}