@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalWearMaterialApi::class, ExperimentalWearMaterialApi::class
)

package com.fekent.medimate.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.RevealValue
import androidx.wear.compose.foundation.rememberRevealState
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.SwipeToRevealCard
import androidx.wear.compose.material.SwipeToRevealDefaults
import com.fekent.medimate.R
import com.fekent.medimate.data.Meds
import com.fekent.medimate.data.meds
import com.fekent.medimate.ui.theme.MediMateTheme
import com.fekent.medimate.ui.viewModels.AppViewModel
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LandingScreen(
    settings: () -> Unit,
    calendar: () -> Unit,
    addMeds: () -> Unit,
    medication: () -> Unit,
    viewModel: AppViewModel = viewModel(factory = AppViewModel.Factory),
    meds: List<Meds>,
    editMed: (Meds) -> Unit,
    deleteMed: (Meds) -> Unit
) {
    val viewState by viewModel.uiState.collectAsState()
    LandingScreenUi(
        settings = { settings() },
        calendar = { calendar() },
        addMeds = { addMeds() },
        medication = { medication() },
        username = viewState.userName,
        meds = meds,
        editMed = editMed,
        deleteMed = deleteMed
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun LandingScreenUi(
    settings: () -> Unit,
    calendar: () -> Unit,
    addMeds: () -> Unit,
    medication: () -> Unit,
    username: String,
    meds: List<Meds>,
    editMed: (Meds) -> Unit,
    deleteMed: (Meds) -> Unit
) {

    Column(Modifier.fillMaxWidth()) {
        LandingBar(settings, calendar)
        Column(
            Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(IntrinsicSize.Max)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(10.dp)
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.plants),
                    contentDescription = "plant leaves",
                    modifier = Modifier
                        .size(100.dp)
                        .offset(x = (-19).dp)
                        .graphicsLayer { rotationZ = 90f }
                )
                Text(
                    text = "Welcome, $username!",
                    fontSize = 24.sp,
                    overflow = TextOverflow.Clip,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .weight(1f)
                )
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
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        shape = RoundedCornerShape(46.dp)
                    ),
                horizontalArrangement = Arrangement.Center
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
                meds.forEach { item ->
                    SwipeMedication(meds = item,
                        medication = { medication() },
                        editMed = { editMed(item) },
                        deleteMed = { deleteMed(item) })
                }

            }
            Spacer(Modifier.size(16.dp))
            HorizontalDivider(
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
            HorizontalDivider(
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
                val sortedDateMeds = meds.sortedBy { it.refill }
                sortedDateMeds.forEach { item ->
                    MedicationRefill(meds = item)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f),
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
                Spacer(modifier = Modifier.weight(1f))
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
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MedicationRefill(meds: Meds) {
    val refillDate = meds.refill
    val formattedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(refillDate)

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
                text = meds.name, textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.weight(1f)
            )
            VerticalDivider(
                modifier = Modifier
                    .height(21.dp)
                    .width(2.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer
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

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun SwipeMedication(
    meds: Meds,
    medication: () -> Unit,
    editMed: (Meds) -> Unit,
    deleteMed: (Meds) -> Unit
) {
    val revealState = rememberRevealState()
    val coroutineScope = rememberCoroutineScope()

    val showDeleteDialog = remember { mutableStateOf(false) }
    if (showDeleteDialog.value) {
        DeleteAlertDialog(
            onDismiss = { showDeleteDialog.value = false },
            onConfirm = { deleteMed(meds); showDeleteDialog.value = false },
            medName = meds.name
        )
    }

    val showEditDialog = remember { mutableStateOf(false) }
    if (showEditDialog.value) {
        EditAlertDialog(
            onDismiss = { showEditDialog.value = false },
            onConfirm = { editMed(meds); showEditDialog.value = false },
            medName = meds.name
        )
    }

    SwipeToRevealCard(
        revealState = revealState,
        onFullSwipe = {
            showDeleteDialog.value = true
            coroutineScope.launch { revealState.animateTo(RevealValue.Covered) }
        },
        primaryAction = {
            IconButton(onClick = {
                showDeleteDialog.value = true
                coroutineScope.launch { revealState.animateTo(RevealValue.Covered) }
            }) {
                Icon(Icons.Default.Delete, "Delete")
            }
        },
        secondaryAction = {
            IconButton(
                onClick = {
                    showEditDialog.value = true
                    coroutineScope.launch { revealState.animateTo(RevealValue.Covered) }
                },
            ) {
                Icon(Icons.Default.Edit, "Edit")
            }
        },
        colors = SwipeToRevealDefaults.actionColors(
            primaryActionBackgroundColor = MaterialTheme.colorScheme.primary,
            secondaryActionBackgroundColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 4.dp)
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        content = {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { medication() }
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
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
                VerticalDivider(
                    modifier = Modifier
                        .height(21.dp)
                        .width(2.dp),
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "${meds.dose} mg",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.weight(0.5f)
                )
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingBar(settings: () -> Unit, calendar: () -> Unit) {
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
        navigationIcon = {
            IconButton(onClick = { calendar() }) {
                Icon(
                    Icons.Filled.DateRange,
                    "Calendar",
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
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


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true, device = Devices.NEXUS_5)
@Preview(showSystemUi = true, device = Devices.PIXEL_6)
@Preview(showSystemUi = true, device = Devices.PIXEL_C)
@Composable
fun LandingPreview() {
    MediMateTheme {
        LandingScreenUi({}, {}, {}, {}, "Emily", meds, {}, {})
    }
}