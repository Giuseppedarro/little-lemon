package com.example.littlelemon

import android.app.Application
import com.example.littlelemon.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LittleLemonApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LittleLemonApplication)
            modules(appModule)
        }
    }
}
