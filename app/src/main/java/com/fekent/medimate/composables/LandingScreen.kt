@file:OptIn(ExperimentalMaterial3Api::class)

package com.fekent.medimate.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fekent.medimate.R
import com.fekent.medimate.data.Meds
import com.fekent.medimate.data.meds
import com.fekent.medimate.ui.theme.MediMateTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LandingScreen(settings: () -> Unit, calendar: () -> Unit, addMeds: () ->Unit, medication: () -> Unit) {

    val username = "Snippy"

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
                    .offset(x = (-19).dp)
                    .graphicsLayer { rotationZ = 90f }
            )
            Box(
                modifier = Modifier
                    .height(100.dp)
            ) {
                Text(
                    text = "Welcome, $username!",
                    fontSize = 24.sp,
                    overflow = TextOverflow.Clip,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(170.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.plants),
                contentDescription = "plant leaves",
                modifier = Modifier
                    .size(100.dp)
                    .offset(19.dp)
                    .graphicsLayer { rotationY = 180f; rotationZ = 90f; }
                    .align(Alignment.CenterVertically)
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .background(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = RoundedCornerShape(46.dp)
                )
        ) {
            Text(
                text = stringResource(id = R.string.info),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.padding(20.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "MEDICATION",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            IconButton(onClick = { addMeds() }) {
                Icon(
                    Icons.Filled.AddCircle,
                    "Add",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                )
            }
        }
        Column(
            modifier = Modifier
                .height(125.dp)
                .verticalScroll(rememberScrollState())
        ) {
            meds.forEach{ item ->
                MedicationRow(meds = item) {
                    
                }
            }

        }
        Spacer(Modifier.size(16.dp))
        Divider(
            Modifier
                .padding(horizontal = 40.dp)
                .background(color = MaterialTheme.colorScheme.primary)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Order Refill Dates",
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Divider(
            Modifier
                .padding(horizontal = 40.dp)
                .background(color = MaterialTheme.colorScheme.primary)
        )
        Spacer(Modifier.size(20.dp))
        Column(
            modifier = Modifier
                .height(125.dp)
                .verticalScroll(rememberScrollState())
        ) {
            val exampleDate =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse("07/07/2024")!!
            MedicationRefill(number = 1, exampleDate)
            MedicationRefill(number = 1, exampleDate)
            MedicationRefill(number = 1, exampleDate)
            MedicationRefill(number = 1, exampleDate)
            MedicationRefill(number = 1, exampleDate)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.plants),
                contentDescription = "Plants",
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .offset(x = (-50).dp)
            )
            IconButton(onClick = { calendar() }) {
                Icon(
                    Icons.Filled.DateRange,
                    "Calendar",
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                        .weight(1f),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            Image(
                painter = painterResource(id = R.drawable.plants),
                contentDescription = "Plants",
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .graphicsLayer { rotationY = 180f }
                    .offset(x = (-50).dp)
            )
        }
    }
}

@Composable
fun MedicationRefill(number: Int, date: Date) {
    val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)

    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 4.dp)
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = number.toString(), textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.weight(0.3f)
            )
            Divider(
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .height(21.dp)
                    .width(2.dp)
            )
            Text(
                text = formattedDate, fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun MedicationRow(meds: Meds, medication: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 4.dp)
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .clickable { medication() }
                .fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = meds.id.toString() ,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.weight(0.3f)
            )
            Divider(
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .height(21.dp)
                    .width(2.dp)
            )
            Text(
                text = meds.name,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimary else Color(
                    53,
                    78,
                    22
                ),
                modifier = Modifier.weight(1f)
            )
            Divider(
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .height(21.dp)
                    .width(2.dp)
            )
            Text(
                text = "${meds.dose} mg",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.weight(0.5f)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
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
                Icon(
                    Icons.Filled.Settings,
                    "Settings",
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
fun LandingPreview() {
    MediMateTheme {
        LandingScreen({}, {}, {}, {})
    }
}