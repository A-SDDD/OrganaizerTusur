package com.danil.ogranizertusur.workspace.workspace_data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkspaceDao {
    @Query("select * from workspaceEntity")
    fun getAllFlow(): Flow<List<WorkSpaceEntity>>

    @Insert
    suspend fun insert(workspaceEntity: WorkSpaceEntity)

    @Update
    suspend fun update(workspaceEntity: WorkSpaceEntity)

    @Delete
    suspend fun delete(workspaceEntity: WorkSpaceEntity)
}