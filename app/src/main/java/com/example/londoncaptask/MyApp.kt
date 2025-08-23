package com.example.londoncaptask

import android.app.Application
import android.content.Context
import com.example.londoncaptask.di.appModule
import com.google.android.play.core.splitcompat.SplitCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class MyApp : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {


        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            workManagerFactory()
            modules(appModule)
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}