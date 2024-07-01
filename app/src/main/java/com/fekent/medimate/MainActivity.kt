package com.fekent.medimate

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.fekent.medimate.composables.AddMedsScreen
import com.fekent.medimate.composables.CalendarScreen
import com.fekent.medimate.composables.LandingScreen
import com.fekent.medimate.composables.MedicationScreen
import com.fekent.medimate.composables.SettingsScreen
import com.fekent.medimate.data.MedsDatabase
import com.fekent.medimate.ui.theme.MediMateTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MediMate(navController: NavHostController) {
    val appContext = LocalContext.current
    val database = remember{
        Room.databaseBuilder(
            appContext,
            MedsDatabase::class.java,
            "Medication"
        ).build()
    }

    NavHost(navController = navController, startDestination = Screen.Landing.route) {
        composable(Screen.Landing.route) {
            LandingScreen(
                settings = { navController.navigate(Screen.Settings.route) },
                calendar = { navController.navigate(Screen.Calendar.route) },
                addMeds = { navController.navigate(Screen.AddMeds.route) },
                medication = { navController.navigate(Screen.Medication.route) },
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(back = {navController.popBackStack(Screen.Landing.route, inclusive = false)})
        }
        composable(Screen.AddMeds.route) { AddMedsScreen(back = {navController.popBackStack(Screen.Landing.route, inclusive = false)})}
        composable(Screen.Calendar.route) { CalendarScreen(back = {navController.popBackStack(Screen.Landing.route, inclusive = false)})}
        composable(Screen.Medication.route) { MedicationScreen(back = {navController.popBackStack(Screen.Landing.route, inclusive = false)})}
    }
}