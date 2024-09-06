@file:OptIn(ExperimentalMaterial3Api::class)

package com.fekent.medimate.composables

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fekent.medimate.BuildConfig
import com.fekent.medimate.R
import com.fekent.medimate.ui.theme.MediMateTheme
import com.fekent.medimate.ui.viewModels.AppViewModel
import com.fekent.medimate.ui.viewModels.ThemeViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SettingsScreen(
    back: () -> Unit,
    appViewModel: AppViewModel = viewModel(factory = AppViewModel.Factory),
    themeViewModel: ThemeViewModel = viewModel(factory = ThemeViewModel.Factory(userRepository = appViewModel.userRepository))
) {
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    var themeChecked by remember { mutableStateOf(isDarkTheme) }
    var notifChecked by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val permissionChecked = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    // Initialize notifChecked based on permission state
    LaunchedEffect(permissionChecked.permissionRequested) {
        notifChecked = permissionChecked.hasPermission
    }

    // Update notifChecked when permission state changes
    LaunchedEffect(permissionChecked.hasPermission) {
        notifChecked = permissionChecked.hasPermission
    }

    SettingsScreenUI(
        username = username,
        onUsernameChange = { username = it },
        onUsernameDone = { appViewModel.saveUserName(username) },
        themeChecked = themeChecked,
        onThemeToggle = { isChecked ->
            themeChecked = isChecked
            themeViewModel.toggleTheme(isChecked)
        },
        notifChecked = notifChecked,
        onNotifToggle = {isChecked ->
            if (isChecked) {
                // Request permission if switch is checked
                permissionChecked.launchPermissionRequest()
            }
            // Update notifChecked to reflect the switch state
            notifChecked = isChecked
        },
        back = back,
        keyboardController = keyboardController,
        isPreview = false
    )
}


@Composable
fun SettingsScreenUI(
    username: String,
    onUsernameChange: (String) -> Unit,
    onUsernameDone: () -> Unit,
    themeChecked: Boolean,
    onThemeToggle: (Boolean) -> Unit,
    notifChecked: Boolean,
    onNotifToggle: (Boolean) -> Unit,
    back: () -> Unit,
    keyboardController: SoftwareKeyboardController? = null,
    isPreview: Boolean = false
) {

    Column(modifier = Modifier.fillMaxSize()) {
        SettingsBar { back() }
        Spacer(Modifier.size(30.dp))
        Column {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(4.dp)
                    ), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Set Username:",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(Modifier.size(8.dp))
                TextField(
                    value = username,
                    onValueChange = onUsernameChange,
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        onUsernameDone()
                        keyboardController?.hide()
                    })
                )
            }
            Spacer(Modifier.size(16.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(4.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Dark Mode",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(Modifier.size(8.dp))
                Switch(
                    checked = themeChecked,
                    onCheckedChange = onThemeToggle,
                    thumbContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.pill),
                            contentDescription = "pill",
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = MaterialTheme.colorScheme.background,
                        checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                        uncheckedThumbColor = Color.Transparent,
                        checkedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        uncheckedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        checkedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        uncheckedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            Spacer(Modifier.size(16.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(4.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Notifications",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(Modifier.size(8.dp))
                Switch(
                    checked = notifChecked,
                    onCheckedChange = onNotifToggle,
                    thumbContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.pill),
                            contentDescription = "pill",
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    },
                    colors = SwitchDefaults.colors(
                        disabledCheckedTrackColor = MaterialTheme.colorScheme.background,
                        disabledCheckedThumbColor = MaterialTheme.colorScheme.onPrimary,
                        disabledUncheckedThumbColor = Color.Transparent,
                        disabledCheckedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        disabledUncheckedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        disabledCheckedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        disabledUncheckedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    enabled = false
                )
            }

            if (BuildConfig.ENABLE_DEVELOPER_OPTIONS){
                Spacer(Modifier.size(30.dp))
                Row(
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .fillMaxWidth()
                ) {
                    if (!isPreview && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        NotificationPermission()
                        AlarmPermission()
                    }

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBar(back: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Settings",
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.primary
            )
        },
        navigationIcon = {
            IconButton(onClick = { back() }) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    "Back",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(60.dp)
                )
            }
        },
        modifier = Modifier
            .padding(4.dp)
            .shadow(elevation = 5.dp, spotColor = Color.DarkGray, shape = RoundedCornerShape(10.dp))
    )
}

@Preview(showSystemUi = true)
@Composable
fun SettingPreview() {
    MediMateTheme {
        SettingsScreenUI(
            username = "Snippy",
            onUsernameChange = {},
            onUsernameDone = {},
            themeChecked = false,
            onThemeToggle = {},
            notifChecked = false,
            onNotifToggle = {},
            back = {},
            keyboardController = null,
            isPreview = true
        )
    }
}