package com.danil.ogranizertusur.bottom_navigation

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen (/*list: MutableState<List<Week>>, weekPagerState: MutableState<Int>*/){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {BottomNavigation(navController = navController)}
    ) {
        NavGraph(navHostController = navController)
    }
}