package com.nasalevich.testapp

import android.app.Application
import com.nasalevich.testapp.koin.AppKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TestAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TestAppApplication)
            modules(AppKoinModule)
        }
    }
}
