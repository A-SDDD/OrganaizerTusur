@file:OptIn(ExperimentalMaterialApi::class)

package com.danil.ogranizertusur.workspace.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity
import com.danil.ogranizertusur.workspace.viewmodel.AddActivityViewModelAbstract
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddActivityLazyColumn(
    addViewModel: AddActivityViewModelAbstract,
    itemListState: State<List<WorkSpaceEntity>>,
    scaffoldState: ScaffoldState,
    status: Boolean,
    onClickNote: (WorkSpaceEntity)->Unit
) {
    val coroutineScope = rememberCoroutineScope()
    LazyColumn {
        items(items = itemListState.value,
            key = { it.roomId ?: "" }
        ) { note ->
            //val note = itemListState.value[index]
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart ||
                        it == DismissValue.DismissedToEnd
                    ) {
                        addViewModel.deleteWorkspace(note)
                        coroutineScope.launch {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Задача удалена",
                                actionLabel = "Отменить"
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                addViewModel.addOrUpdateWorkspace(
                                    WorkSpaceEntity(
                                        date = note.date,
                                        time = note.time,
                                        text = note.text,
                                        status = note.status
                                    )

                                )
                            }
                        }
                        return@rememberDismissState true
                    }
                    return@rememberDismissState false
                }
            )
            if(note.status == status){

                NoteListItem(
                    modifier = Modifier.animateItemPlacement(),
                    onClick = {
                        addViewModel.selectedNote(note)
                        onClickNote(note)
                    },
                    onDelete = {
                        addViewModel.deleteWorkspace(note)
                    },
                    note = note,
                    dismissState = dismissState,
                    addViewModel
                )
            }
        }
    }
}