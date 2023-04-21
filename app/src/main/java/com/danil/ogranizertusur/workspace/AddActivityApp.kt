package com.danil.ogranizertusur.workspace

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AddActivityApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}