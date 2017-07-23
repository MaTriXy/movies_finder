package com.ronen.moviesfinder.components

import android.app.Application
import com.ronen.moviesfinder.managers.ContentManager
import com.ronen.moviesfinder.modules.di.AppModule
import com.ronen.moviesfinder.modules.di.NetworkModule
import com.ronen.moviesfinder.ui.fragments.BaseFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class))
interface AppComponent {

    fun inject(app: Application)
    fun inject(contentManager: ContentManager)
    fun inject(baseFragment: BaseFragment)

}
