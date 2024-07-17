package com.fekent.medimate

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.fekent.medimate.composables.AddMedsScreen
import com.fekent.medimate.composables.CalendarScreen
import com.fekent.medimate.composables.LandingScreen
import com.fekent.medimate.composables.MedicationScreen
import com.fekent.medimate.composables.SettingsScreen
import com.fekent.medimate.data.Meds
import com.fekent.medimate.data.MedsDatabase
import com.fekent.medimate.ui.theme.MediMateTheme
import com.fekent.medimate.ui.viewModels.ThemeViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
class MainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels { ThemeViewModel.Factory() }

    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Refill"
            val descriptionText = "Refill Medication"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel("refill", name, importance)
            mChannel.description = descriptionText
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
        var builder = NotificationCompat.Builder(this, "refill")
            .setSmallIcon(R.drawable.pill)
            .setContentTitle("Refill")
            .setContentText("Medication Refill Due")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
            MediMateTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    MediMate(navController = rememberNavController(), themeViewModel = themeViewModel)
                    }
                }
            }
        }
    }


sealed class Screen(val route: String) {
    object Landing : Screen("Landing")
    object Settings : Screen("Settings")
    object AddMeds : Screen("AddMeds")
    object EditMeds : Screen("EditMeds/{medId}")
    object Calendar : Screen("Calendar")
    object Medication : Screen("Medication")

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MediMate(navController: NavHostController, themeViewModel: ThemeViewModel) {
    val appContext = LocalContext.current
    val database = remember {
        Room.databaseBuilder(
            appContext,
            MedsDatabase::class.java,
            "Medication"
        ).build()
    }

    NavHost(navController = navController, startDestination = Screen.Landing.route) {
        composable(Screen.Landing.route) {
            val landingScreenScope = rememberCoroutineScope()
            val meds by database.medsDao().allMeds().collectAsState(initial = emptyList())

            LandingScreen(
                settings = { navController.navigate(Screen.Settings.route) },
                calendar = { navController.navigate(Screen.Calendar.route) },
                addMeds = { navController.navigate(Screen.AddMeds.route) },
                medication = { navController.navigate(Screen.Medication.route) },
                meds = meds,
                editMed = {med -> navController.navigate("EditMeds/${med.id}")},
                deleteMed = {med -> landingScreenScope.launch { database.medsDao().delete(med) }}
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(back = {
                navController.popBackStack(
                    Screen.Landing.route,
                    inclusive = false
                )
            }, themeViewModel = themeViewModel)
        }
        composable(Screen.AddMeds.route) {
            val addScreenScope = rememberCoroutineScope()
            AddMedsScreen(back = {
                navController.popBackStack(
                    Screen.Landing.route,
                    inclusive = false
                )
            }, onMedEntered = { newMeds ->
                addScreenScope.launch {
                    database.medsDao().insertMeds(newMeds)
                }
                navController.popBackStack(Screen.Landing.route, inclusive = false)
            }, medToEdit = null)
        }
        composable(
            Screen.EditMeds.route,
            arguments = listOf(navArgument("medId") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val editScreenScope = rememberCoroutineScope()
            val medId = navBackStackEntry.arguments?.getInt("medId")
            if (medId != null) {
                var med: Meds? by remember { mutableStateOf(null) }

                LaunchedEffect(key1 = medId) {
                    med = database.medsDao().getMeds(medId)
                }
                med?.let { editedMed ->
                    AddMedsScreen(back = {
                        navController.popBackStack(
                            Screen.Landing.route,
                            inclusive = false
                        )
                    }, onMedEntered = { updatedMed ->
                        editScreenScope.launch {
                            database.medsDao().editMeds(updatedMed)
                        }
                        navController.popBackStack(Screen.Landing.route, inclusive = false)
                    }, medToEdit = editedMed)
                }
            }


        }
        composable(Screen.Calendar.route) {
            CalendarScreen(back = {
                navController.popBackStack(
                    Screen.Landing.route,
                    inclusive = false
                )
            })
        }
        composable(Screen.Medication.route) {
            MedicationScreen(back = {
                navController.popBackStack(
                    Screen.Landing.route,
                    inclusive = false
                )
            })
        }
    }
}