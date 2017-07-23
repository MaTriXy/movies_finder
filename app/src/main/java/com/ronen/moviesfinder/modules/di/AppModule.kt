package com.ronen.moviesfinder.modules.di

import android.arch.persistence.room.Room
import com.ronen.moviesfinder.MoviesFinderApp
import com.ronen.moviesfinder.db.Database
import com.ronen.moviesfinder.managers.ContentManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: MoviesFinderApp) {

    @Provides
    @Singleton
    fun provideApplication() = app

    @Provides
    @Singleton
    fun provideDb(): Database = Room.databaseBuilder(app, Database::class.java, "videos-db").build()

    @Provides
    @Singleton
    fun provideConnectionManager(): ContentManager {
        return ContentManager()
    }

//    @Provides
//    @Singleton
//    fun provideContentManager() = ContentManager(app)
//
//    @Provides
//    @Singleton
//    fun provideApiManager() = ApiManager()
}