package com.danil.ogranizertusur.schedule

import android.app.Application
import androidx.room.Room
import com.danil.ogranizertusur.schedule.data.data_source.ScheduleDatabase
import com.danil.ogranizertusur.schedule.data.repository.ScheduleRepositoryImpl
import com.danil.ogranizertusur.schedule.domain.repository.ScheduleRepository
import com.danil.ogranizertusur.schedule.domain.use_case.DeleteSchedule
import com.danil.ogranizertusur.schedule.domain.use_case.GetScheduleCase
import com.danil.ogranizertusur.schedule.domain.use_case.ScheduleUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn((SingletonComponent::class))
object AppModule {

    @Provides
    @Singleton
    fun provideScheduleDatabase(app:Application):ScheduleDatabase{
        return Room.databaseBuilder(
            app,
            ScheduleDatabase::class.java,
            ScheduleDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideScheduleRepository(db: ScheduleDatabase): ScheduleRepository{
        return ScheduleRepositoryImpl(db.scheduleDao)
    }
    @Provides
    @Singleton
    fun provideScheduleUseCases(repository: ScheduleRepository): ScheduleUseCases{
        return ScheduleUseCases(
            getSchedule = GetScheduleCase(repository),
            deleteSchedule = DeleteSchedule(repository),
            //setSchedule = SetSchedule(repository)
        )
    }
}