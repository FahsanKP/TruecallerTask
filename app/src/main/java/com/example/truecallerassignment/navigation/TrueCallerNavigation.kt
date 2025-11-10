package com.example.truecallerassignment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.truecallerassignment.presentation.MainScreen
import kotlinx.serialization.Serializable

@Serializable
object MainRoute

@Composable
fun TruecallerNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = MainRoute
    ) {
        composable<MainRoute> {
            MainScreen()
        }
    }
}
