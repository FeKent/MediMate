package com.fekent.medimate.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface MedsDao {
    @Insert
    suspend fun insertMeds(meds: Meds)

    @Delete
    suspend fun delete(meds: Meds)

    @Update
    suspend fun editMeds(meds: Meds)

    @Query ("SELECT * FROM meds")
    fun allMeds(): Flow<List<Meds>>

    @Query("SELECT * FROM meds WHERE meds.id = :medsId LIMIT 1")
    suspend fun getMeds(medsId: Int): Meds

    @Query("SELECT refill FROM meds ORDER BY refill ASC")
    suspend fun getOrderedRefillDates(): List<LocalDate>
}