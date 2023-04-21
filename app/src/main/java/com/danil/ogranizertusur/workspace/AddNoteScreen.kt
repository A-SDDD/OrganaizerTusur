package com.danil.ogranizertusur.workspace

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity
import com.danil.ogranizertusur.workspace.viewmodel.AddActivityViewModelAbstract

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    addViewModel: AddActivityViewModelAbstract,
    onClickClose: () -> Unit,
    // onClickSave: ()->Unit
) {
    val note = addViewModel.selectedNoteState.value
    val textState = rememberSaveable { mutableStateOf(note?.text ?: " ") }
    val dateState = rememberSaveable { mutableStateOf(note?.date ?: " ") }
    val timeState = rememberSaveable { mutableStateOf(note?.time ?: " ") }
    val statusState = rememberSaveable { mutableStateOf(note?.status ?: false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "edit ")
                },
                actions = {
                    /*  Row(
                          modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                      ) {*/

                    IconButton(onClick = {
                        note?.let {
                            addViewModel.addOrUpdateWorkspace(
                                it.copy(
                                    date = dateState.value,
                                    time = timeState.value,
                                    text = textState.value,
                                    status = statusState.value
                                )
                            )
                        } ?: run {
                            addViewModel.addOrUpdateWorkspace(
                                WorkSpaceEntity(
                                    date = dateState.value,
                                    time = timeState.value,
                                    text = textState.value,
                                    status = statusState.value
                                )
                            )
                        }
                        onClickClose()
                    }) {
                        Icon(imageVector = Icons.Rounded.Done, contentDescription = "Save")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onClickClose) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Close")
                    }
                }


            )
        }

    ) {

        Column(
            modifier = Modifier
                .padding(it)
                // .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colors.background)
        ) {

            BasicTextField(modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .fillMaxWidth(),
                //  .fillMaxHeight(0.3f),
                value = dateState.value,
                onValueChange = { date ->
                    dateState.value = date
                }
            )

            BasicTextField(modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .fillMaxWidth(),
                //   .fillMaxHeight(0.3f),
                value = timeState.value,
                onValueChange = { time ->
                    timeState.value = time
                }
            )

            BasicTextField(modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .fillMaxWidth(),
                //.fillMaxHeight(0.3f),
                value = textState.value,
                onValueChange = { txt ->
                    textState.value = txt
                }
            )


        }
    }
}