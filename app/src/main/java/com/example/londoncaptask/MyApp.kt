package com.example.londoncaptask

import android.app.Application
import com.example.londoncaptask.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {


        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(appModule)
        }
    }
}