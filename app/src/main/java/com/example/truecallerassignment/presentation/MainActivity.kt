package com.example.truecallerassignment.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.truecallerassignment.navigation.TruecallerNavigation
import com.example.truecallerassignment.presentation.theme.TrueCallerAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrueCallerAssignmentTheme {
                val navController = rememberNavController()
                TruecallerNavigation(navController = navController)
            }
        }
    }
}