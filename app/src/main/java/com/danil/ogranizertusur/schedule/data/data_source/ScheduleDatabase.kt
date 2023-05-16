package com.danil.ogranizertusur.schedule.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danil.ogranizertusur.schedule.domain.model.Schedule


@Database(
    entities = [Schedule::class],
    version = 1
)
abstract class ScheduleDatabase: RoomDatabase() {
    abstract val scheduleDao: ScheduleDao
    companion object{
        const val DATABASE_NAME="schedule_db"
    }
}