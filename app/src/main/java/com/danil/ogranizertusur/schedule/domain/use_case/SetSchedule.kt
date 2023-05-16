package com.danil.ogranizertusur.schedule.domain.use_case

import com.danil.ogranizertusur.schedule.domain.model.Schedule
import com.danil.ogranizertusur.schedule.domain.repository.ScheduleRepository

class SetSchedule(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(schedule: Schedule){
        repository.insertSchedule(schedule)
    }
}