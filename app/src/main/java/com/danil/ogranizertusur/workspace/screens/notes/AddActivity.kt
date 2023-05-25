package com.danil.ogranizertusur.workspace.screens.notes

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity
import com.danil.ogranizertusur.workspace.viewmodel.AddActivityViewModelAbstract
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.danil.ogranizertusur.R
import com.danil.ogranizertusur.ui.theme.*
import com.danil.ogranizertusur.workspace.screens.notes.components.AddActivityLazyColumn
import com.danil.ogranizertusur.workspace.screens.notes.components.TabLayout
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun AddActivity(
    addViewModel: AddActivityViewModelAbstract,
    onClickNote: (WorkSpaceEntity) -> Unit,
    onClickAddNote: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val itemListState = addViewModel.workspaceListFlow.collectAsState(initial = listOf())
    val tabList = remember{
        mutableStateListOf(
            "Все",
            "Сегодня",
            "Завтра"
        )
    }
    val pagerState = rememberPagerState(initialPage = 0)
    val tabIndex = pagerState.currentPage
    val font = FontStyle(
        R.font.anton_regular
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
                 CenterAlignedTopAppBar(
                     colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colors.background),
                     title = {
                         Text(
                             text = "Задачи",
                             fontSize = 24.sp,
                             color = MaterialTheme.colors.onSecondary,
                             fontStyle = font,
                             fontWeight = FontWeight.Bold
                         )
                     }
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
                modifier = Modifier
                    .wrapContentWidth(CenterHorizontally),
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, "")
            }
        })
    {

        Box(
            modifier = Modifier
                .padding(it)
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
        ) {
            Column {
                TabLayout(
                    tabList = tabList,
                    pagerState = pagerState,
                    tabIndex = tabIndex
                )
                LazyColumn (modifier = Modifier.background(MaterialTheme.colors.background)){
                    item {

                        Text(
                            text = "Актуальные:",
                            fontSize = 18.sp,
                            color = LightBlue,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                        )
                        AddActivityLazyColumn(
                            addViewModel = addViewModel,
                            itemListState = itemListState,
                            scaffoldState = scaffoldState,
                            status = false,
                            positionLayout = pagerState.currentPage,
                            onClickNote = onClickNote
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                        )
                        Text(
                            text = "Завершенные:",
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(start = 16.dp),
                            color = DarkRed,
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                        )
                        AddActivityLazyColumn(
                            addViewModel = addViewModel,
                            itemListState = itemListState,
                            scaffoldState = scaffoldState,
                            status = true,
                            positionLayout = pagerState.currentPage,
                            onClickNote = onClickNote
                        )
                    }
                }
            }
        }
    }
}


