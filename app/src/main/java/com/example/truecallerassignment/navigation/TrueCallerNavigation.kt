package com.example.truecallerassignment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.truecallerassignment.presentation.MainScreen
import kotlinx.serialization.Serializable

/**
 * Navigation route for the Main screen
 */
@Serializable
object MainRoute

/**
 * Truecaller Navigation Graph
 * Defines the navigation structure for the entire app
 * @param navController The navigation controller that manages the back stack
 */
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
