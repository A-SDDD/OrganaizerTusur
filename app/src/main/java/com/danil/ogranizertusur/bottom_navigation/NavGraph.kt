package com.danil.ogranizertusur.bottom_navigation

import com.danil.ogranizertusur.workspace.AddNoteScreen
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
import com.danil.ogranizertusur.schedule.TabDaysOfWeek
import com.danil.ogranizertusur.workspace.AddActivity
import com.danil.ogranizertusur.workspace.viewmodel.AddActivityViewModel


@Composable
fun NavGraph(
    navHostController: NavHostController,
    addViewModel: AddActivityViewModel,/*, list: MutableState<List<Week>>, weekPagerState: MutableState<Int>*/
) {

    NavHost(navController = navHostController, startDestination = "screen_1") {

        composable("screen_1") {
            Image(
                painter = painterResource(id = R.drawable.fon),
                contentDescription = "fon1",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.7f),
                contentScale = ContentScale.FillBounds
            )
            TabDaysOfWeek()
        }

        composable("screen_2") {
            Image(
                painter = painterResource(id = R.drawable.fon),
                contentDescription = "fon2",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.7f),
                contentScale = ContentScale.FillBounds
            )
            AddActivity(
                addViewModel = addViewModel,
                onClickNote = {
                    navHostController.navigate("addNote")
                },
                onClickAddNote = {
                    navHostController.navigate("addNote")
                }
            )
        }

        composable("screen_3") {
            Image(
                painter = painterResource(id = R.drawable.fon),
                contentDescription = "fon3",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.7f),
                contentScale = ContentScale.FillBounds
            )
            TabDaysOfWeek()
        }

        composable("screen_4") {
            Image(
                painter = painterResource(id = R.drawable.fon),
                contentDescription = "fon4",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.7f),
                contentScale = ContentScale.FillBounds
            )
            TabDaysOfWeek()
        }

        composable("addNote") {
            AddNoteScreen(addViewModel,
                onClickClose = {
                    navHostController.popBackStack()
                }

            )
        }

    }
}