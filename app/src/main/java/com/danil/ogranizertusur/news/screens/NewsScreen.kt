package com.danil.ogranizertusur.news.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.danil.ogranizertusur.news.screens.calendar_events.EventsPager
import com.danil.ogranizertusur.news.screens.news.NewsPager
import com.danil.ogranizertusur.workspace.screens.notes.components.TabLayout
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun NewsScreen(
    onClickNews: ()->Unit,
    onClickEvent: ()->Unit
) {
    val pagerState = rememberPagerState()
    val tabList = remember{ mutableStateListOf("Новости","События")}
    val tabIndex = remember{0}

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                   Text(
                           tabList[pagerState.currentPage]
                       )
                }
            )
        },
        bottomBar = {
            BottomAppBar(modifier = Modifier.fillMaxHeight(0.08f)) {}
        }
    ) {
        Column (modifier = Modifier.padding(it)){
            TabLayout(
                tabList = tabList,
                pagerState = pagerState,
                tabIndex = tabIndex
            )
            when (pagerState.currentPage) {
                0-> NewsPager(onClickNews)
                1-> EventsPager(onClickEvent)
            }
        }


    }
}
