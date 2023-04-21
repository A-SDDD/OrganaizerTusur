package com.danil.ogranizertusur.workspace.workspace_data.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity
import com.danil.ogranizertusur.workspace.workspace_data.WorkspaceDao
import kotlinx.coroutines.flow.Flow

class WorkspaceRepository(private val workspaceDao: WorkspaceDao) {

    fun getAllFlow(): Flow<List<WorkSpaceEntity>> = workspaceDao.getAllFlow()

    suspend fun insert(workspaceEntity: WorkSpaceEntity) = workspaceDao.insert(workspaceEntity = workspaceEntity)

    suspend fun update(workspaceEntity: WorkSpaceEntity) = workspaceDao.update(workspaceEntity = workspaceEntity)


    suspend fun delete(workspaceEntity: WorkSpaceEntity) = workspaceDao.delete(workspaceEntity = workspaceEntity)
}
