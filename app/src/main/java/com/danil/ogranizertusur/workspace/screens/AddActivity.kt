package com.danil.ogranizertusur.workspace.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity
import com.danil.ogranizertusur.workspace.viewmodel.AddActivityViewModelAbstract


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddActivity(
    addViewModel: AddActivityViewModelAbstract,
    onClickNote: (WorkSpaceEntity) -> Unit,
    onClickAddNote: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val itemListState = addViewModel.workspaceListFlow.collectAsState(initial = listOf())
    Scaffold(
        scaffoldState = scaffoldState,
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
        Column() {
            Text(
                text = "Актуальные:",
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
            AddActivityLazyColumn(
                addViewModel = addViewModel,
                itemListState = itemListState,
                scaffoldState = scaffoldState,
                status = false,
                onClickNote = onClickNote
            )
            Text(
                text = "Завершенные:",
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
            AddActivityLazyColumn(
                addViewModel = addViewModel,
                itemListState = itemListState,
                scaffoldState = scaffoldState,
                status = true,
                onClickNote = onClickNote
            )

        }

    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewAddActivity() {
    //AddActivity()
}