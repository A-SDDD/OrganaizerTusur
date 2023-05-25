package com.danil.ogranizertusur

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import com.danil.ogranizertusur.bottom_navigation.MainScreen
import com.danil.ogranizertusur.schedule.screens.ScheduleViewModel
import com.danil.ogranizertusur.ui.theme.OgranizerTusurTheme
import com.danil.ogranizertusur.ui.theme.darkBack
import com.danil.ogranizertusur.ui.theme.fontTitleTypography
import com.danil.ogranizertusur.workspace.viewmodel.AddActivityViewModel
import com.danil.ogranizertusur.workspace.viewmodel.Graph
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val addViewModel: AddActivityViewModel by viewModels()
        val scheduleViewModel:ScheduleViewModel by viewModels()

        Graph.provide(this)
        createNotificationChannel()
        setContent {
            OgranizerTusurTheme (darkTheme = false){
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)
                Column() {
                    MainScreen(addViewModel = addViewModel, scheduleViewModel = scheduleViewModel)
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val name:CharSequence = "channelFirstOT"
            val description = "Channel for Organizer TUSUR"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("channelFirst",name,importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}


