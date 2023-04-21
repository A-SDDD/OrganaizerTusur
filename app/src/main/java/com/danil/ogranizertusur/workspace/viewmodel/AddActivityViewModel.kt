package com.danil.ogranizertusur.workspace.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity
import com.danil.ogranizertusur.workspace.workspace_data.repository.WorkspaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface AddActivityViewModelAbstract {

    val selectedNoteState: State<WorkSpaceEntity?>
    val workspaceListFlow: Flow<List<WorkSpaceEntity>>

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

    override val workspaceListFlow: Flow<List<WorkSpaceEntity>> = workspaceRepository.getAllFlow()


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

}