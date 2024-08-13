package com.fekent.medimate.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class CalendarDay(val date: LocalDate, val isCurrentMonth: Boolean)

@RequiresApi(Build.VERSION_CODES.O)
fun getDaysInMonth(year: Int, month: Int): List<CalendarDay> {
    val firstDayOfMonth = LocalDate.of(year, month, 1)
    val lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth())

    // Get the first day of the week for the month (e.g., Sunday, Monday)
    val firstWeekDay = firstDayOfMonth.dayOfWeek.value
    val days = mutableListOf<CalendarDay>()

    // Add days before the start of the month
    var day = firstDayOfMonth.minusDays(firstWeekDay.toLong() - 1)
    while (day.isBefore(firstDayOfMonth)) {
        days.add(CalendarDay(date = day, false))
        day = day.plusDays(1)
    }

    // Add days of the current month
    day = firstDayOfMonth
    while (day.isBefore(lastDayOfMonth.plusDays(1))) {
        days.add(CalendarDay(date = day, true))
        day = day.plusDays(1)
    }

    // Add days after the end of the month
    day = lastDayOfMonth.plusDays(1)
    val lastWeekDay = lastDayOfMonth.dayOfWeek.value
    val daysToAddAfterMonth = 7 - lastWeekDay
    repeat(daysToAddAfterMonth) {
        days.add(CalendarDay(date = day, false))
        day = day.plusDays(1)
    }

    return days
}