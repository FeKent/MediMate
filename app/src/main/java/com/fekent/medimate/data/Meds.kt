package com.fekent.medimate.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Meds(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val name: String,
    val dose: Int,
    val pillCount: Int,
    val refill: LocalDate
)

@RequiresApi(Build.VERSION_CODES.O)
val meds = listOf(
    Meds(0,  "Sertraline", 100, 28, LocalDate.of(2024, 6, 10)),
    Meds(1, "Sertraline", 100, 28, LocalDate.of(2024, 4, 2)),
    Meds(2, "Sertraline", 100, 28, LocalDate.of(2024, 7, 30)),
    Meds(3,"Sertraline", 100, 28, LocalDate.of(2024, 11, 28)),
    Meds(4, "Sertraline", 100, 28, LocalDate.of(2024, 8, 17)),
)