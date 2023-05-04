package com.danil.ogranizertusur.workspace.di

import android.app.Application
import com.danil.ogranizertusur.workspace.workspace_data.AppDatabase
import com.danil.ogranizertusur.workspace.workspace_data.WorkspaceDao
import com.danil.ogranizertusur.workspace.workspace_data.repository.WorkspaceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class WsModule {

    @Singleton
    @Provides
    fun provideWorkspaceRepository(
        workspaceDao: WorkspaceDao,
    ): WorkspaceRepository {
        return WorkspaceRepository(workspaceDao = workspaceDao)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): AppDatabase {
        return AppDatabase.getInstance(context = app)
    }


    @Singleton
    @Provides
    fun provideWorkspaceDao(appDatabase: AppDatabase): WorkspaceDao {
        return appDatabase.workspaceDao()
    }
}