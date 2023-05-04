package com.danil.ogranizertusur.schedule.data.repository

import com.danil.ogranizertusur.schedule.data.data_source.ScheduleDao
import com.danil.ogranizertusur.schedule.domain.model.Schedule
import com.danil.ogranizertusur.schedule.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class ScheduleRepositoryImpl (
    private val dao: ScheduleDao
    ) : ScheduleRepository{
    override fun getSchedule(): Flow<List<Schedule>> {
        return dao.getSchedule()
    }

    override suspend fun getScheduleById(id: Int): Schedule? {
        return dao.getScheduleById(id)
    }

    override suspend fun insertSchedule(schedule: Schedule) {
        return dao.insertSchedule(schedule)
    }

    override suspend fun deleteSchedule(schedule: Schedule) {
        return dao.deleteSchedule(schedule)
    }

}