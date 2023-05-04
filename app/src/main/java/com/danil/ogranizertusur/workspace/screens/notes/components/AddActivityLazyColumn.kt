@file:OptIn(ExperimentalMaterialApi::class)

package com.danil.ogranizertusur.workspace.screens.notes.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity
import com.danil.ogranizertusur.workspace.viewmodel.AddActivityViewModelAbstract
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun AddActivityLazyColumn(
    addViewModel: AddActivityViewModelAbstract,
    itemListState: State<List<WorkSpaceEntity>>,
    scaffoldState: ScaffoldState,
    status: Boolean,
    positionLayout: Int,
    onClickNote: (WorkSpaceEntity)->Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val currentDate = LocalDate.now().plusDays(0).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    val tomorrowDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    Column() {

        itemListState.value.forEachIndexed {index, note->

            key( note.roomId?:"") {
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart /*||
                        it == DismissValue.DismissedToEnd*/
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
                when (positionLayout) {
                    0 -> {
                        if (note.status == status) {
                            Log.d("DateNoteList", "${note.date} == $currentDate, $positionLayout")
                            NoteListItem(
                                modifier = Modifier,
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
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                            )
                        }
                    }
                    1 -> {
                        if (note.status == status) {
                            Log.d("DateNoteList", "${note.date} == $currentDate, $positionLayout")
                            if (note.date == currentDate) {
                                NoteListItem(
                                    modifier = Modifier,
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
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(10.dp)
                                )
                            }
                        }
                    }
                    2 -> {
                        if (note.status == status) {
                            Log.d(
                                "DateNoteList",
                                "${note.date} == ${tomorrowDate}, $positionLayout"
                            )
                            if (note.date == tomorrowDate) {
                                NoteListItem(
                                    modifier = Modifier,
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
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(10.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

    }

   /* LazyColumn (
        modifier = Modifier.fillMaxWidth()
            .height(500.dp)
        ){
        items(items = itemListState.value,
            key = { it.roomId ?: "" }
        ) { note ->
            //val note = itemListState.value[index]
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart /*||
                        it == DismissValue.DismissedToEnd*/
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
            when (positionLayout){
                0 ->{if(note.status == status){
                    Log.d("DateNoteList", "${note.date} == $currentDate, $positionLayout")
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
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp))
                }}
                1->{if(note.status == status){
                    Log.d("DateNoteList", "${note.date} == $currentDate, $positionLayout")
                    if(note.date == currentDate){
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
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp))
                }}}
                2->{if(note.status == status){
                    Log.d("DateNoteList", "${note.date} == ${tomorrowDate}, $positionLayout")
                    if(note.date == tomorrowDate){
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
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp))
                }}}
            }

        }
    }*/
}