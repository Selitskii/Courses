package com.example.courses

import android.app.Application
import com.example.courses.di.appModule
import com.example.courses.di.viewModelModule
import org.koin.android.ext.android.startKoin
import org.koin.standalone.StandAloneContext.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin(this, listOf(appModule, viewModelModule))
    }

    companion object{
        lateinit var instance:App
            private set
    }

}