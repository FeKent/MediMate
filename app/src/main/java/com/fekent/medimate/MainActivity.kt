package com.fekent.medimate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fekent.medimate.ui.theme.MediMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediMateTheme {
                MediMate(navController = rememberNavController())
            }
        }
    }
}

sealed class Screen(val route: String){
    object Landing : Screen("Landing")
    object Settings : Screen("Settings")
}

@Composable
fun MediMate(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.Landing.route){
        composable(Screen.Landing.route){}
        composable(Screen.Settings.route){}
    }

}