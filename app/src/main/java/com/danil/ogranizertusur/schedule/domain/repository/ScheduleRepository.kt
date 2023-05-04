package com.danil.ogranizertusur.schedule.domain.repository

import com.danil.ogranizertusur.schedule.domain.model.Schedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {

    fun getSchedule(): Flow<List<Schedule>>

    suspend fun getScheduleById(id: Int): Schedule?

    suspend fun insertSchedule(schedule: Schedule)

    suspend fun deleteSchedule(schedule: Schedule)
}