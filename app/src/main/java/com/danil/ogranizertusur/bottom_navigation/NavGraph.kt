package com.danil.ogranizertusur.bottom_navigation

import com.danil.ogranizertusur.workspace.screens.add_edit_notes.AddNoteScreen
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
import com.danil.ogranizertusur.news.screens.NewsInfo
import com.danil.ogranizertusur.news.screens.NewsScreen
import com.danil.ogranizertusur.schedule.screens.TabDaysOfWeek
import com.danil.ogranizertusur.workspace.screens.notes.AddActivity
import com.danil.ogranizertusur.workspace.viewmodel.AddActivityViewModel


@Composable
fun NavGraph(
    navHostController: NavHostController,
    addViewModel: AddActivityViewModel,
) {
    NavHost(navController = navHostController, startDestination = "screen_1") {

        composable("screen_1") {

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
            /*Image(
                painter = painterResource(id = R.drawable.fon),
                contentDescription = "fon4",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.7f),
                contentScale = ContentScale.FillBounds
            )*/
            NewsScreen(onClickNews = {
                navHostController.navigate("newsInfo")
            }
            )
        }

        composable("addNote") {
            AddNoteScreen(addViewModel,
                onClickClose = {
                    navHostController.popBackStack()
                },

            )
        }
        composable("newsInfo"){
            NewsInfo()
        }

    }
}