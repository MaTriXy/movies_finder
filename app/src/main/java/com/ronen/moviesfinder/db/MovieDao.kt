package com.ronen.moviesfinder.db

import android.arch.persistence.room.*
import com.ronen.moviesfinder.modules.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movies "
            + "WHERE id = :movieId")
    fun getMovieById(movieId: Long): Movie

    @Update
    fun update(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM movies")
    fun deleteAll()
}
