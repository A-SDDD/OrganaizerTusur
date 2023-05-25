package com.danil.ogranizertusur.workspace.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.danil.ogranizertusur.workspace.alarm_scheduler.AndroidAlarmScheduler
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity
import com.danil.ogranizertusur.workspace.workspace_data.repository.WorkspaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

interface AddActivityViewModelAbstract {

    val selectedNoteState: State<WorkSpaceEntity?>
    val workspaceListFlow: Flow<List<WorkSpaceEntity>>
    val scheduler: AndroidAlarmScheduler

    fun convertToLocalDateTime(dateString: String, timeString: String): LocalDateTime
    fun addOrUpdateWorkspace(workspace: WorkSpaceEntity)
    fun updateWorkspace(workspace: WorkSpaceEntity)
    fun deleteWorkspace(workspace: WorkSpaceEntity)
    fun selectedNote(workspace: WorkSpaceEntity)
    fun resetSelectedNote()

}

@HiltViewModel
class AddActivityViewModel
@Inject constructor(
    private val workspaceRepository: WorkspaceRepository,


    ) : ViewModel(), AddActivityViewModelAbstract {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _selectedNoteState: MutableState<WorkSpaceEntity?> = mutableStateOf(null)
    override val selectedNoteState: State<WorkSpaceEntity?>
        get() = _selectedNoteState

    override val workspaceListFlow: Flow<List<WorkSpaceEntity>> =
        workspaceRepository.getAllFlow().map { notes ->
            notes.sortedWith(
                compareBy({
                    LocalDate.parse(
                        it.date,
                        DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    )
                },
                    {
                        LocalTime.parse(
                            it.time,
                            when (it.time.length) {
                                4 -> DateTimeFormatter.ofPattern("H:mm")
                                else -> DateTimeFormatter.ofPattern("HH:mm")
                            }
                        )
                    }
                )
            )
        }
    private val _scheduler:AndroidAlarmScheduler = AndroidAlarmScheduler(Graph.appContext)
    override val scheduler: AndroidAlarmScheduler
        get() = _scheduler

    override fun addOrUpdateWorkspace(workspace: WorkSpaceEntity) {
        ioScope.launch {
            if (workspace.roomId == null) {

                workspaceRepository.insert(workspaceEntity = workspace)
            } else {
                workspaceRepository.update(workspaceEntity = workspace)

            }
        }
    }


    override fun updateWorkspace(workspace: WorkSpaceEntity) {
        ioScope.launch {
            workspaceRepository.update(workspaceEntity = workspace)
        }
    }

    override fun deleteWorkspace(workspace: WorkSpaceEntity) {
        ioScope.launch {
            workspaceRepository.delete(workspaceEntity = workspace)
        }
    }

    override fun selectedNote(workspace: WorkSpaceEntity) {
        _selectedNoteState.value = workspace
    }

    override fun resetSelectedNote() {
        _selectedNoteState.value = null
    }

    override fun convertToLocalDateTime(dateString: String, timeString: String): LocalDateTime {
        val dateTimeString = "$dateString $timeString"
        val dateTimeFormat: DateTimeFormatter
        if (timeString.length == 5) {
            dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        }else{
            dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy H:mm")
        }
        return LocalDateTime.parse(dateTimeString, dateTimeFormat)
    }
}

