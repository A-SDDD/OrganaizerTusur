package com.danil.ogranizertusur.workspace.room_model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workspaceEntity")
data class WorkSpaceEntity(
    @PrimaryKey(autoGenerate = true) var roomId: Long? = null,
    val date: String,
    val time: String,
    val text: String,
    val status: Boolean
)
