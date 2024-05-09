package com.fekent.medimate.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Meds(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val name: String,
    val dose: Int,
    val pillCount: Int,
    val refill: Date
)

val meds = listOf(
    Meds(0,  "Sertraline", 100, 28, Date(2024,1 ,8)),
    Meds(1, "Sertraline", 100, 28, Date(2024,7 ,7)),
    Meds(2, "Sertraline", 100, 28, Date(2024,7 ,7)),
    Meds(3,"Sertraline", 100, 28, Date(2024,7 ,7)),
    Meds(4, "Sertraline", 100, 28, Date(2024,7 ,7)),
)