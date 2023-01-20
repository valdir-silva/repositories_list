package com.valdirsilva.repositorieslist.application

import android.app.Application
import com.valdirsilva.repositorieslist.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@Application)
            koin.loadModules(listOf(appModule))
        }
    }
}
