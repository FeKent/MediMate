package com.fekent.medimate.composables

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fekent.medimate.data.Meds
import com.fekent.medimate.ui.theme.MediMateTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddMedsScreen(back: () -> Unit, onMedEntered: (Meds) -> Unit, medToEdit: Meds? = null) {
    var name by remember { mutableStateOf(medToEdit?.name ?: "") }
    var dose by remember { mutableStateOf(medToEdit?.dose?.toString() ?: "") }
    var pillCount by remember { mutableStateOf(medToEdit?.pillCount?.toString() ?: "") }

    var isDoneActionTriggered by remember { mutableStateOf(false) }
    var refillChecked by remember { mutableStateOf(false) }
    var dailyChecked by remember { mutableStateOf(false) }
    var neededChecked by remember { mutableStateOf(false) }
    val keyboardManager = LocalFocusManager.current
    val editMode = medToEdit != null
    val context = LocalContext.current

    val showValidLogState = remember { mutableStateOf(false) }
    val validationLabel = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        AddMedsBar(back = { back() }, title = "${if (editMode) "Edit" else "Add"} Medication")
        Spacer(Modifier.size(24.dp))
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Medication Name") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Words
                )
            )
            Spacer(Modifier.size(8.dp))
            TextField(
                value = dose,
                onValueChange = { dose = it },
                label = { Text(text = "Dosage") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                )
            )
            Spacer(Modifier.size(8.dp))
            TextField(
                value = pillCount,
                onValueChange = { pillCount = it; isDoneActionTriggered = false },
                label = { Text(text = "Total Pill Count") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(onDone = {
                    isDoneActionTriggered = true; keyboardManager.clearFocus()
                })
            )
            Spacer(Modifier.size(16.dp))


            if (!neededChecked) { // Show only if 'As Needed Medication' is not checked
                Row {
                    Text(
                        text = "Medication Prescribed Daily?", fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Checkbox(
                        checked = dailyChecked,
                        onCheckedChange = {
                            dailyChecked = it
                            if (it) neededChecked = false // Uncheck 'As Needed' checkbox when 'Daily' is checked
                        },
                        colors = CheckboxDefaults.colors(uncheckedColor = MaterialTheme.colorScheme.primary)
                    )
                }
            }

            // Row for the "As Needed Medication" checkbox
            if (!dailyChecked) { // Show only if 'Medication Prescribed Daily' is not checked
                Row {
                    Text(
                        text = "As Needed Medication?", fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Checkbox(
                        checked = neededChecked,
                        onCheckedChange = {
                            neededChecked = it
                            if (it) dailyChecked = false // Uncheck 'Daily' checkbox when 'As Needed' is checked
                        },
                        colors = CheckboxDefaults.colors(uncheckedColor = MaterialTheme.colorScheme.primary)
                    )
                }
            }


            Spacer(Modifier.size(16.dp))
            val dateEntered = remember { LocalDate.now() }
            val refillDate = dateEntered.plusDays(pillCount.toLongOrNull() ?: 0)
            val formattedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(refillDate)
            val orderRefill = minusWorkingDays(7, refillDate)
            val formattedOrderRefill =
                DateTimeFormatter.ofPattern("dd/MM/yyyy").format(orderRefill)


            if (pillCount.isNotEmpty() && isDoneActionTriggered && dailyChecked) {

                Row {
                    Text(
                        text = "Meds Used By:",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(Modifier.size(4.dp))
                    Text(
                        text = formattedDate.toString(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(Modifier.size(16.dp))
                Row {
                    Text(
                        text = "Add Refill Reminder?",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Checkbox(
                        checked = refillChecked,
                        onCheckedChange = { refillChecked = it },
                        colors = CheckboxDefaults.colors(uncheckedColor = MaterialTheme.colorScheme.primary)
                    )
                }


                if (refillChecked) {
                    Spacer(Modifier.size(16.dp))
                    Row {
                        Text(
                            text = "Order Refill On:",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(Modifier.size(4.dp))
                        Text(
                            text = formattedOrderRefill.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(Modifier.size(32.dp))
            IconButton(onClick = {

                var emptyFieldsCount = 0
                if (name.isEmpty()) {
                    emptyFieldsCount++
                }
                if (dose.isEmpty()) {
                    emptyFieldsCount++
                }
                if (pillCount.isEmpty()) {
                    emptyFieldsCount++
                }

                validationLabel.value = when (emptyFieldsCount) {
                    0 -> ""
                    1 -> when {
                        name.isEmpty() -> "Name"
                        dose.isEmpty() -> "Dose"
                        pillCount.isEmpty() -> "Pill Count"
                        else -> ""
                    }

                    else -> "Multiple"
                }

                if (emptyFieldsCount > 0) {
                    showValidLogState.value = true
                    return@IconButton
                }

                val newMeds = Meds(
                    id = medToEdit?.id ?: 0,
                    name = name,
                    dose = dose.toInt(),
                    pillCount = pillCount.toInt(),
                    refill = if (refillChecked) orderRefill else refillDate
                )
                onMedEntered.invoke(newMeds)
                if (refillChecked) {
                    createRefillAlarm(context.applicationContext, newMeds)
                }
            }) {
                Icon(
                    Icons.Filled.AddCircle,
                    "Save Button",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(60.dp)
                )
            }
        }

        if (showValidLogState.value) {
            ValidationDialog(label = validationLabel.value,
                onDismiss = { showValidLogState.value = false })
        }

    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun minusWorkingDays(days: Int, date: LocalDate): LocalDate {
    var refillDate = date
    var remainingDays = days

    while (remainingDays > 0) {
        refillDate = refillDate.minusDays(1)
        if (refillDate.dayOfWeek == DayOfWeek.SATURDAY || refillDate.dayOfWeek == DayOfWeek.SUNDAY) {
            remainingDays
        } else {
            remainingDays--
        }
    }
    return refillDate
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedsBar(back: () -> Unit, title: String) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.primary
        )
    }, navigationIcon = {
        IconButton(onClick = { back() }) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                "Back",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(60.dp)
            )
        }
    }, modifier = Modifier
        .padding(4.dp)
        .shadow(
            elevation = 5.dp,
            spotColor = Color.DarkGray,
            shape = RoundedCornerShape(10.dp)
        )
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun AddMedsPreview() {
    MediMateTheme {
        AddMedsScreen({}, {})
    }
}