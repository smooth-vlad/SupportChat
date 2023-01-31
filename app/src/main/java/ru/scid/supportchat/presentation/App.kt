package ru.scid.supportchat.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        private lateinit var _instance: App

        fun getInstance(): App {
            return _instance
        }

    }

    init {
        _instance = this
    }

    override fun onCreate() {
        super.onCreate()

    }
}