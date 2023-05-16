package com.danil.ogranizertusur.schedule.screens

import com.danil.ogranizertusur.schedule.domain.model.Schedule

data class ScheduleState(
    val schedule: List<Schedule> = emptyList(),
    val isOrderSectionVisible:Boolean = false
)
