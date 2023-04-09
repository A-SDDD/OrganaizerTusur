package com.danil.ogranizertusur.bottom_navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.danil.ogranizertusur.R
import com.danil.ogranizertusur.TabDaysOfWeek
import com.danil.ogranizertusur.workspace.AddActivity

@Composable
fun NavGraph(navHostController: NavHostController/*, list: MutableState<List<Week>>, weekPagerState: MutableState<Int>*/) {

NavHost(navController = navHostController, startDestination = "screen_1"){
    composable("screen_1"){
        Image(painter = painterResource(id = R.drawable.fon),
            contentDescription = "fon1",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.7f),
            contentScale = ContentScale.FillBounds)
        TabDaysOfWeek()
    }
    composable("screen_2"){
        Image(painter = painterResource(id = R.drawable.fon),
            contentDescription = "fon1",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.7f),
            contentScale = ContentScale.FillBounds)
        AddActivity()
    }
    composable("screen_3"){
        Image(painter = painterResource(id = R.drawable.fon),
            contentDescription = "fon1",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.7f),
            contentScale = ContentScale.FillBounds)
        TabDaysOfWeek()
    }
    composable("screen_4"){
        Image(painter = painterResource(id = R.drawable.fon),
            contentDescription = "fon1",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.7f),
            contentScale = ContentScale.FillBounds)
        TabDaysOfWeek()
    }
}
}