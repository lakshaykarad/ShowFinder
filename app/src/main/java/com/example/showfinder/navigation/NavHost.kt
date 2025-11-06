package com.example.showfinder.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.showfinder.ui.theme.detailscreen.DetailScreen
import com.example.showfinder.ui.theme.detailscreen.DetailsScreenViewModel
import com.example.showfinder.ui.theme.home.HomeScreen
import com.example.showfinder.utils.Screen


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val viewModel : DetailsScreenViewModel = viewModel()
    NavHost(navController = navController, startDestination = Screen.Home.routes){
        composable (Screen.Home.routes){ HomeScreen(navController = navController) }
        composable (
            route = Screen.DetailScreem.routes,
            arguments = listOf(navArgument("imdbId") {type = NavType.StringType} )
        ){backStackEntry->
            val imdbId = backStackEntry.arguments?.getString("imdbId") ?: ""
            DetailScreen(imdbId = imdbId,navController = navController, viewModel = viewModel)
        }

    }
}