package com.ronen.moviesfinder.db

import android.arch.persistence.room.*
import com.ronen.moviesfinder.modules.Video

@Dao
interface VideoDao {

    @Query("SELECT * FROM videos")
    fun getAll(): List<Video>

    @Query("SELECT * FROM videos "
            + "WHERE movie_id = :movieId")
    fun getVideosForMovieId(movieId: Long): List<Video>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(videos: List<Video>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(video: Video)

    @Delete
    fun delete(video: Video)

    @Query("DELETE FROM videos")
    fun deleteAll()
}
