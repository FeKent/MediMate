@file:OptIn(ExperimentalMaterial3Api::class)

package com.fekent.medimate.composables

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fekent.medimate.ui.theme.MediMateTheme


@Composable
fun SettingsScreen(back: () -> Unit) {
    var themeChecked by remember { mutableStateOf(false) }
    var notifChecked by remember { mutableStateOf(false) }

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
                    value = "Snippy",
                    onValueChange = {},
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
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
                    onCheckedChange = { themeChecked = it },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = MaterialTheme.colorScheme.background,
                        checkedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        uncheckedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        checkedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        uncheckedIconColor = MaterialTheme.colorScheme.onSecondaryContainer
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
                    onCheckedChange = { notifChecked = it },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = MaterialTheme.colorScheme.background,
                        checkedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        uncheckedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        checkedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        uncheckedIconColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
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
                    Icons.Filled.KeyboardArrowLeft,
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
        SettingsScreen {}
    }
}