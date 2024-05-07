@file:OptIn(ExperimentalMaterial3Api::class)

package com.fekent.medimate.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fekent.medimate.R
import com.fekent.medimate.ui.theme.MediMateTheme

@Composable
fun LandingScreen(settings: () -> Unit) {

    val username = "User"

    Column(Modifier.fillMaxWidth()) {
        LandingBar { settings() }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(IntrinsicSize.Max)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(10.dp)
                ),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.plants),
                contentDescription = "plant leaves",
                modifier = Modifier
                    .size(100.dp)
                    .offset(x = (-21).dp)
                    .graphicsLayer { rotationZ = 90f }
            )
            Text(
                text = "Welcome, $username",
                fontSize = 24.sp,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Image(
                painter = painterResource(id = R.drawable.plants),
                contentDescription = "plant leaves",
                modifier = Modifier
                    .size(100.dp)
                    .offset(20.dp)
                    .graphicsLayer { rotationY = 180f; rotationZ = 90f; }
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun LandingBar(settings: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "MediMate",
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary
            )
        },
        actions = {
            IconButton(onClick = { settings() }) {
                Icon(Icons.Filled.Settings, "Settings", tint = MaterialTheme.colorScheme.secondary)
            }
        },
        modifier = Modifier
            .padding(4.dp)
            .shadow(elevation = 5.dp, spotColor = Color.DarkGray, shape = RoundedCornerShape(10.dp))
    )
}


@Preview(showSystemUi = true)
@Composable
fun LandingPreview() {
    MediMateTheme {
        LandingScreen {}
    }
}