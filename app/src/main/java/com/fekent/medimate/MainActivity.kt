package com.fekent.medimate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fekent.medimate.composables.LandingScreen
import com.fekent.medimate.ui.theme.MediMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediMateTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MediMate(navController = rememberNavController())
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Landing : Screen("Landing")
    object Settings : Screen("Settings")
}

@Composable
fun MediMate(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.Landing.route) {
        composable(Screen.Landing.route) {
            LandingScreen(settings = { navController.navigate(Screen.Settings.route) }, calendar = {})
        }
        composable(Screen.Settings.route) {}
    }
}