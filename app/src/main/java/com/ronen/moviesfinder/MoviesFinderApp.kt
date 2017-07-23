package com.ronen.moviesfinder

import android.app.Application
import com.ronen.moviesfinder.components.AppComponent
import com.ronen.moviesfinder.components.DaggerAppComponent
import com.ronen.moviesfinder.modules.di.AppModule

class MoviesFinderApp : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
        appComponent.inject(this)
    }
}