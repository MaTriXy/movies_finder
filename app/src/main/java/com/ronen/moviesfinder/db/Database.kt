package com.ronen.moviesfinder.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.ronen.moviesfinder.modules.Movie
import com.ronen.moviesfinder.modules.Video


@Database(entities = arrayOf(
        Movie::class,
        Video::class
), version = 1)
abstract class Database : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun videoDao(): VideoDao
}
