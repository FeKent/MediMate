package com.fekent.medimate.composables

import android.Manifest
import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
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
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
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
}

class RefillAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationChannelID = "refill"
        val medName = intent.getStringExtra("med_name") ?: return
        val notification = NotificationCompat.Builder(context, notificationChannelID)
            .setContentTitle("Refill $medName")
            .setContentText("Time to Order $medName")
            .setSmallIcon(R.drawable.pill)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(Random.nextInt(), notification)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun createRefillAlarm(context: Context, med: Meds) {
    val alarmManager = context.applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context.applicationContext, RefillAlarmReceiver::class.java).apply {
        putExtra("med_name", med.name)
    }
    val now = LocalDateTime.now()
    val pendingIntent = PendingIntent.getBroadcast(context, med.id, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    val alarmTime = med.refill.atTime(LocalTime.now().plusMinutes(5)).toInstant(ZoneOffset.systemDefault().rules.getOffset(now)).toEpochMilli()

    val alarmDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(alarmTime), ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

    // Log the date and time of the alarm
    Log.d("AlarmTime", "Alarm is set for: ${alarmDateTime.format(formatter)}")

    alarmManager.setExactAndAllowWhileIdle(RTC_WAKEUP, alarmTime, pendingIntent)
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

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AlarmPermission() {
    val alarmPermission = rememberPermissionState(permission = Manifest.permission.USE_EXACT_ALARM)

    if (alarmPermission.hasPermission) {
        Log.d("AddMedsScreen", "USE_EXACT_ALARM permission granted")
    } else {
        Log.d("AddMedsScreen", "USE_EXACT_ALARM permission denied")
    }
}