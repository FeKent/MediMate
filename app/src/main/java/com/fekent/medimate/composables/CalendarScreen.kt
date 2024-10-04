@file:OptIn(ExperimentalMaterial3Api::class)

package com.fekent.medimate.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fekent.medimate.data.Meds
import com.fekent.medimate.data.getDaysInMonth
import com.fekent.medimate.data.meds
import com.fekent.medimate.data.refillDates
import com.fekent.medimate.ui.theme.MediMateTheme
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(back: () -> Unit, refillDates: List<LocalDate>, meds: List<Meds>) {
    Column(modifier = Modifier.fillMaxSize()) {
        var currentDate by remember { mutableStateOf(LocalDate.now()) }

        CalendarBar { back() }
        Spacer(Modifier.size(8.dp))
        CalendarHeader(
            currentDate = currentDate,
            previous = { currentDate = currentDate.minusMonths(1) },
            next = { currentDate = currentDate.plusMonths(1) })
        Spacer(Modifier.size(4.dp))
        HorizontalDivider(
            Modifier
                .padding(horizontal = 30.dp)
                .background(color = MaterialTheme.colorScheme.primary)
        )
        Spacer(Modifier.size(24.dp))
        CalendarView(currentDate = currentDate, refillDates = refillDates, meds = meds)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarHeader(currentDate: LocalDate, previous: () -> Unit, next: () -> Unit) {
    Row(modifier = Modifier.padding(horizontal = 32.dp)) {
        val month = currentDate.month
        val year = currentDate.year.toString()
        Text(
            text = "$month, $year",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        IconButton(onClick = { previous() }) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                "Previous Month",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        IconButton(onClick = { next() }) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                "Next Month",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarView(currentDate: LocalDate, refillDates: List<LocalDate>, meds: List<Meds>) {
    val year = currentDate.year
    val month = currentDate.monthValue
    val daysInMonth = getDaysInMonth(year, month)

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedMeds by remember { mutableStateOf<List<Meds?>>(emptyList()) }

    if (showBottomSheet) {
        ModalBottomSheet(onDismissRequest = { showBottomSheet = false }, sheetState = sheetState) {
            if (selectedMeds.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    selectedMeds.forEach { med -> // Loop through selected meds and display them
                        Text(
                            text = "${med?.name}: ${med?.dose}mg", fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }


    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        // Display day headers
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            val weekDays = DayOfWeek.entries.toTypedArray()
            for (day in weekDays) {
                Text(
                    text = day.name.take(3), // Get the first 3 letters of the day name
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        // Display days
        val rows = daysInMonth.chunked(7)
        for (week in rows) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                for (day in week) {
                    val isRefillDate =
                        day.isCurrentMonth && refillDates.contains(day.date) // Check if the date is in the refillDates
                    val isToday = day.date == LocalDate.now()

                    val backgroundColor = when {
                        isToday -> MaterialTheme.colorScheme.primaryContainer // Today's date
                        isRefillDate -> Color.Blue.copy(alpha = 0.3f) // Refill date background
                        else -> MaterialTheme.colorScheme.background // Default background
                    }

                    val textModifier = when {
                        isRefillDate -> Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .fillMaxHeight()
                            .background(backgroundColor)
                            .border(
                                1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .clickable {
                                val associatedMeds: List<Meds?> =
                                    meds.filter { med -> med.refill == day.date }
                                    selectedMeds = selectedMeds + associatedMeds
                                    showBottomSheet = true
                                    scope.launch { sheetState.show() }

                            }
                            .wrapContentHeight(align = Alignment.CenterVertically)

                        day.isCurrentMonth -> Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .fillMaxHeight()
                            .background(backgroundColor)
                            .border(
                                1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .wrapContentHeight(align = Alignment.CenterVertically)

                        else -> Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .fillMaxHeight()
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    }
                    Text(
                        text = day.date.dayOfMonth.toString(),
                        color = when {
                            day.isCurrentMonth -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.onBackground
                        },
                        fontWeight = if (day.isCurrentMonth) FontWeight.SemiBold else FontWeight.Light,
                        modifier = textModifier,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        textDecoration = if (isToday) {
                            TextDecoration.Underline
                        } else {
                            TextDecoration.None
                        },
                        lineHeight = TextUnit(50f, TextUnitType.Sp),
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarBar(back: () -> Unit) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = "Calendar",
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.primary
        )
    }, actions = {
        IconButton(onClick = { back() }) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                "Back",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(60.dp)
            )
        }
    }, modifier = Modifier
        .padding(4.dp)
        .shadow(elevation = 5.dp, spotColor = Color.DarkGray, shape = RoundedCornerShape(10.dp))
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun CalendarPreview() {
    MediMateTheme {
        CalendarScreen({}, refillDates = refillDates, meds = meds)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun CalendarViews() {
    CalendarView(currentDate = LocalDate.of(2024, 9, 28), refillDates = refillDates, meds = meds)
}