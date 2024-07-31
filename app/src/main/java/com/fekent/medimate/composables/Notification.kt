package com.fekent.medimate.composables

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import com.fekent.medimate.R
import com.fekent.medimate.data.Meds
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlin.random.Random


class NotificationHandler(private val context: Context) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    private val notificationChannelID = "refill"

    fun showSimpleNotification() {
        val notification = NotificationCompat.Builder(context, notificationChannelID)
            .setContentTitle("Simple Notification")
            .setContentText("Message or text with notification")
            .setSmallIcon(R.drawable.pill)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()  // finalizes the creation

        notificationManager.notify(Random.nextInt(), notification)
    }

    fun refillNotification(med: Meds){
        val notification = NotificationCompat.Builder(context, notificationChannelID)
            .setContentTitle("Refill ${med.name}")
            .setContentText("Time to Order ${med.name}")
            .setSmallIcon(R.drawable.pill)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationPermission() {
    val context = LocalContext.current
    val postNotificationPermission =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    val notificationHandler = NotificationHandler(context)

    LaunchedEffect(key1 = true) {
        if (!postNotificationPermission.hasPermission) {
            postNotificationPermission.launchPermissionRequest()
        } else {
            postNotificationPermission.permissionRequested
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = {
                notificationHandler.showSimpleNotification()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) { Text(text = "Simple notification") }
    }
}
