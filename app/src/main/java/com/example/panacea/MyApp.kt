package com.example.panacea

import android.app.Application
import com.example.panacea.koin.appModule
import com.example.panacea.koin.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApp)
            modules(appModule)
        }
    }
}