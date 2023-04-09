package com.danil.ogranizertusur

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import com.danil.ogranizertusur.bottom_navigation.MainScreen
import com.danil.ogranizertusur.ui.theme.OgranizerTusurTheme
import kotlinx.coroutines.*
import java.util.*


class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {




            OgranizerTusurTheme {
            Column(){
                MainScreen ()
            }
        }}
    }
}


