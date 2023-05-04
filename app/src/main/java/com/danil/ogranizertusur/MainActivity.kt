package com.danil.ogranizertusur

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import com.danil.ogranizertusur.bottom_navigation.MainScreen
import com.danil.ogranizertusur.ui.theme.OgranizerTusurTheme
import com.danil.ogranizertusur.workspace.viewmodel.AddActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val addViewModel: AddActivityViewModel by viewModels()
        setContent {
            OgranizerTusurTheme (){
                Column() {
                    MainScreen(addViewModel = addViewModel)
                }
            }
        }
    }
}


