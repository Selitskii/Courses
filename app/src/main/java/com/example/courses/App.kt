package com.example.courses

import android.app.Application
import com.example.courses.di.appModule
import com.example.courses.di.viewModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin(this, listOf(appModule, viewModule))
    }

    companion object {
        lateinit var instance: App
            private set
    }

}