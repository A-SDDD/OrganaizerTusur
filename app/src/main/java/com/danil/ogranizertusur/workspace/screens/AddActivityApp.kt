package com.danil.ogranizertusur.workspace.screens

import android.app.Application
import com.danil.ogranizertusur.workspace.viewmodel.Graph
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AddActivityApp: Application() {
    override fun onCreate() {
        super.onCreate()
        //Graph.provide(this)
    }
}