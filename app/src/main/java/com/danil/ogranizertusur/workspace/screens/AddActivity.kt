@file:OptIn(ExperimentalMaterialApi::class)

package com.danil.ogranizertusur.workspace.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity
import com.danil.ogranizertusur.workspace.viewmodel.AddActivityViewModelAbstract


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddActivity(
    addViewModel: AddActivityViewModelAbstract,
    onClickNote: (WorkSpaceEntity) -> Unit,
    onClickAddNote: () -> Unit,
) {
    val itemListState = addViewModel.workspaceListFlow.collectAsState(initial = listOf())
    /*val popupState = rememberSaveable { mutableStateOf(PopupState.Close) }
    val textState = rememberSaveable { mutableStateOf("") }
    val noteId: MutableState<Long?> = rememberSaveable { mutableStateOf(null) }*/

    Scaffold(
        topBar = {
                 CenterAlignedTopAppBar(
                     title = {Text(text = "Задачи", fontSize = 24.sp)}
                 )
        },

        bottomBar = {
            BottomAppBar {
            }
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    addViewModel.resetSelectedNote()
                    onClickAddNote()
                },
                modifier = Modifier.wrapContentWidth(CenterHorizontally),
                backgroundColor = Color.Blue,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, "")
            }
        })
    {
        LazyColumn {
            items(items = itemListState.value,
            key = {it.roomId ?: ""}
                ) { note ->
                //val note = itemListState.value[index]
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart ||
                            it == DismissValue.DismissedToEnd) {
                            addViewModel.deleteWorkspace(note)

                            return@rememberDismissState true
                        }
                        return@rememberDismissState false
                    }
                )
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
                    dismissState = dismissState
                )
            }
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewAddActivity() {
    //AddActivity()
}