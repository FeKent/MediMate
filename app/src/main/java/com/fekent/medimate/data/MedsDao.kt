package com.fekent.medimate.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MedsDao{
    @Insert
    suspend fun insertMeds(meds: Meds)

    @Delete
    suspend fun deleteMeds(meds: Meds)

    @Update
    suspend fun editMeds(meds: Meds)

    @Query("SELECT * FROM meds")
    suspend fun allMeds(meds: Meds): Flow<List<Meds>>

    @Query("SELECT * FROM meds WHERE meds.id = :medsId LIMIT 1")
    suspend fun getMed(medsId: Int): Meds
}