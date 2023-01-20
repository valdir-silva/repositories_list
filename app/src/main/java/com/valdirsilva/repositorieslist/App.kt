package com.valdirsilva.repositorieslist

import android.app.Application
import com.valdirsilva.repositorieslist.data.api.ApiService

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        ApiService.init(this)
    }
}
