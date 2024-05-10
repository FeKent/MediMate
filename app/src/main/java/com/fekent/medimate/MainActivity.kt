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
import com.fekent.medimate.composables.AddMedsScreen
import com.fekent.medimate.composables.CalendarScreen
import com.fekent.medimate.composables.LandingScreen
import com.fekent.medimate.composables.SettingsScreen
import com.fekent.medimate.ui.theme.MediMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediMateTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MediMate(navController = rememberNavController())
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Landing : Screen("Landing")
    object Settings : Screen("Settings")
    object AddMeds : Screen("AddMeds")
    object Calendar : Screen("Calendar")
    object Medication : Screen("Medication")

}

@Composable
fun MediMate(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.Landing.route) {
        composable(Screen.Landing.route) {
            LandingScreen(
                settings = { navController.navigate(Screen.Settings.route) },
                calendar = { navController.navigate(Screen.Calendar.route) },
                addMeds = { navController.navigate(Screen.AddMeds.route) },
                medication = { navController.navigate(Screen.Medication.route) }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(back = { navController.navigate(Screen.Landing.route) })
        }
        composable(Screen.AddMeds.route) { AddMedsScreen(back = {navController.navigate(Screen.Landing.route)})}
        composable(Screen.Calendar.route) { CalendarScreen(back = { navController.navigate(Screen.Landing.route) }) }
        composable(Screen.Medication.route) {}
    }
}