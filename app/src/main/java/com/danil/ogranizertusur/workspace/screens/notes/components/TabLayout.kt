package com.danil.ogranizertusur.workspace.screens.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(
    tabList: SnapshotStateList<String>,
    pagerState: PagerState,
    tabIndex: Int
){
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.clip(RoundedCornerShape(5.dp))
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { pos ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, pos)
                )
            },

            modifier = Modifier
                .fillMaxWidth(1.0f)
                .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
                .fillMaxHeight(0.05f)
                .alpha(0.9f),
            backgroundColor = Color.White
        )
        {
            tabList.forEachIndexed { index, text ->

                Tab(
                    selected = false, onClick = {
                        coroutineScope.launch(Dispatchers.Main) {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(text = text, color = Color(0xFF2B5CA8))
                    },
                    modifier = Modifier
                        .background(
                            Color.White,
                            RoundedCornerShape(20.dp)
                        )
                        .border(2.dp,Color(0xFF2B5CA8), shape = RoundedCornerShape(20.dp))
                        .fillMaxWidth(0.4f)
                )
            }
        }
        HorizontalPager(
            count = tabList.size,
            state = pagerState
        ) {

        }
    }
}