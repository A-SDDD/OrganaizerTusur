package com.danil.ogranizertusur.bottom_navigation

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.danil.ogranizertusur.schedule.screens.ScheduleViewModel
import com.danil.ogranizertusur.workspace.viewmodel.AddActivityViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen (addViewModel: AddActivityViewModel, scheduleViewModel: ScheduleViewModel /*list: MutableState<List<Week>>, weekPagerState: MutableState<Int>*/){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {BottomNavigation(navController = navController)}
    ) {
        NavGraph(navHostController = navController, addViewModel = addViewModel,scheduleViewModel)
    }
}