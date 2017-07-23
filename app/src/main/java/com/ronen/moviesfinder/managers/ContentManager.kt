package com.ronen.moviesfinder.managers

import android.util.Log
import com.ronen.moviesfinder.MoviesFinderApp
import com.ronen.moviesfinder.db.Database
import com.ronen.moviesfinder.modules.Movie
import com.ronen.moviesfinder.modules.MoviesResponse
import com.ronen.moviesfinder.network.MoviesApi
import com.ronen.moviesfinder.utils.NetworkUtils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ContentManager {

    @Inject
    lateinit var moviesApi: MoviesApi

    @Inject
    lateinit var db: Database

    @Inject
    lateinit var app: MoviesFinderApp

    init {
        MoviesFinderApp.appComponent.inject(this)
    }

    companion object {
        val TAG: String = "ContentManager"
    }

    fun getNowPlaying(page: Int, callback: MoviesListener) {
        if (NetworkUtils.isConnectionAvailable(app)) {
            getRemoteMovies(page, callback)
        } else {
            getStoredMovies(callback)
        }
    }

    private fun getRemoteMovies(page: Int, callback: MoviesListener?) {
        moviesApi.getNowPlaying(page)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map { moviesResponse ->
                    if (page == 1) {
                        db.videoDao().deleteAll()
                        db.movieDao().deleteAll()
                    }
                    db.movieDao().insertAll(moviesResponse.movies)
                    moviesResponse
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    moviesResponse ->
                    Log.d(TAG, "Got moviesResponse $moviesResponse")
                    callback?.let { callback.onMoviesReady(moviesResponse) }
                }, {
                    error ->
                    Log.d(TAG, "Got error $error")
                    callback?.let { callback.onMoviesError() }
                })
    }

    fun getMovie(movieId: Long, callback: MovieListener) {
        if (NetworkUtils.isConnectionAvailable(app)) {
            getRemoteMovie(movieId, callback)
        } else {
            getStoredMovie(movieId, callback)
        }
    }

    private fun getRemoteMovie(movieId: Long, callback: MovieListener?) {
        moviesApi.getMovie(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map { movie ->
                    movie.parseVideos(movie.videoJson?.get("results")?.asJsonArray)
                    db.videoDao().insertAll(movie.videosList)
                    movie
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    movie ->
                    Log.d(TAG, "Got movie $movie")
                    callback?.let { callback.onMovieReady(movie) }
                }, {
                    error ->
                    Log.d(TAG, "Got error $error")
                    callback?.let { callback.onMovieError() }
                })
    }

    fun getStoredMovies(callback: MoviesListener) {
        Single.fromCallable {
            db.movieDao().getAll()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    movies ->
                    Log.d(TAG, "Got videos $movies")
                    callback.onMoviesReady(MoviesResponse(movies))
                }, {
                    error ->
                    Log.d(TAG, "Got error $error")
                    callback.onMoviesError()
                })
    }

    fun getStoredMovie(movieId: Long, callback: MovieListener) {
        Single.fromCallable {
            db.movieDao().getMovieById(movieId)
        }.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map { movie ->
                    val videos = db.videoDao().getVideosForMovieId(movie.id!!)
                    movie.videosList.addAll(videos)
                    movie
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    movie ->
                    Log.d(TAG, "Got videos $movie")
                    callback.onMovieReady(movie)
                }, {
                    error ->
                    Log.d(TAG, "Got error $error")
                    callback.onMovieError()
                })
    }

    interface MoviesListener {
        fun onMoviesReady(moviesResponse: MoviesResponse)
        fun onMoviesError()
    }

    interface MovieListener {
        fun onMovieReady(movie: Movie)
        fun onMovieError()
    }
}