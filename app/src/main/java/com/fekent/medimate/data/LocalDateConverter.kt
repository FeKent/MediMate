package com.fekent.medimate.data

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
import java.time.format.DateTimeParseException

class LocalDateConverter {
    private val DATE_PATTERN = "dd-MM-yyyy"
    @SuppressLint("NewApi")
    private val DATE_FORMATTER: DateTimeFormatter = ofPattern(DATE_PATTERN)

    @SuppressLint("NewApi")
    @TypeConverter
    fun fromString(value: String?): LocalDate? {
        return if (!value.isNullOrEmpty()) {
            try {
                LocalDate.parse(value, DATE_FORMATTER)
            } catch (@SuppressLint("NewApi") e: DateTimeParseException) {
                null
            }
        } else {
            null
        }
    }

    @SuppressLint("NewApi")
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(DATE_FORMATTER)
    }
}