package com.danil.ogranizertusur.schedule.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danil.ogranizertusur.schedule.domain.model.Schedule
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM schedule")
    fun getSchedule(): Flow<List<Schedule>>

    @Query("SELECT * FROM schedule WHERE id = :id")
    suspend fun getScheduleById(id: Int): Schedule?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: Schedule)

    @Delete
    suspend fun deleteSchedule(schedule: Schedule)
}