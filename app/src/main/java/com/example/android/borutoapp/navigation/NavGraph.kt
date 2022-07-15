package com.example.android.borutoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.android.borutoapp.presentation.screens.details.DetailsScreen
import com.example.android.borutoapp.presentation.screens.home.HomeScreen
import com.example.android.borutoapp.presentation.screens.search.SearchScreen
import com.example.android.borutoapp.presentation.screens.splash.SplashScreen
import com.example.android.borutoapp.presentation.screens.welcome.WelcomeScreen
import com.example.android.borutoapp.util.Constants.DETAILS_ARGUMENT_KEY

@Composable
fun SetUpNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navHostController)
        }
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController = navHostController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navHostController)
        }
        composable(
            Screen.Details.route,
            arguments = listOf(
                navArgument(DETAILS_ARGUMENT_KEY) {
                    type = NavType.IntType
                }
            )
        ) {
            DetailsScreen(navHostController)
        }
        composable(Screen.Search.route) {
            SearchScreen(navController = navHostController)
        }
    }
}