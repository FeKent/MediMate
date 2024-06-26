package com.fekent.medimate.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Meds::class], version = 1)
abstract class MedsDatabase : RoomDatabase() {
    abstract fun medsDao(): MedsDao
}