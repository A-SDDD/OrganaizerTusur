package com.danil.ogranizertusur.workspace

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity
import com.danil.ogranizertusur.workspace.viewmodel.AddActivityViewModelAbstract


@OptIn(ExperimentalFoundationApi::class)
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
            items(itemListState.value.size) { index ->
                val note = itemListState.value[index]

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = {
                                addViewModel.selectedNote(note)
                                onClickNote(note)
                            },
                            onLongClick = { addViewModel.deleteWorkspace(note) }
                        )

                        .height(54.dp),
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart),
                        text = note.date
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = note.time
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterEnd),
                        text = note.text
                    )
                }


            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(1f),
                    contentAlignment = Alignment.BottomEnd
                ) {

                }
            }

        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewAddActivity() {
    //AddActivity()
}