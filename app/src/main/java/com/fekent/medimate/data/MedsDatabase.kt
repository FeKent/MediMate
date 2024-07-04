package com.fekent.medimate.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Meds::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateConverter::class)
abstract class MedsDatabase : RoomDatabase() {
    abstract fun medsDao(): MedsDao
}