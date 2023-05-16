package com.danil.ogranizertusur.schedule.screens

import androidx.lifecycle.ViewModel
import com.danil.ogranizertusur.schedule.domain.model.Schedule
import com.danil.ogranizertusur.schedule.domain.repository.ScheduleRepository
import com.danil.ogranizertusur.schedule.domain.use_case.ScheduleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleUseCases: ScheduleUseCases,
    private val scheduleRepository: ScheduleRepository
):ViewModel(){
    val scheduleListFlow:Flow<List<Schedule>> = scheduleUseCases.getSchedule()
    private val ioScope = CoroutineScope(Dispatchers.IO)

    fun deleteGroupAndFaculty(){
        ioScope.launch {
            scheduleUseCases.deleteSchedule
        }
    }
    fun setSchedule(schedule: Schedule){
        ioScope.launch {
            //scheduleUseCases.setSchedule(schedule = schedule)
            scheduleRepository.insertSchedule(schedule)
        }
    }
}