package com.danil.ogranizertusur.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.danil.ogranizertusur.R
import com.danil.ogranizertusur.schedule.ThemeStatusObj

@Composable
fun SettingsScreen (){
    Scaffold(
        topBar = {
            TopAppBar(
            title = {
                Text(
                    text = "Настройки",
                    fontSize = 24.sp
                )
                    },
               backgroundColor = MaterialTheme.colors.background,

        )
        },
        bottomBar = {
            BottomAppBar {
            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Image(
                painter = if(ThemeStatusObj.theme){
                    painterResource(id = R.drawable.fon_night)
                }else{
                    painterResource(id = R.drawable.fon)
                },
                contentDescription = "fon",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.7f),
                contentScale = ContentScale.FillBounds
            )
        }
    }

}