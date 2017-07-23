package com.ronen.moviesfinder.network

import com.ronen.moviesfinder.modules.Movie
import com.ronen.moviesfinder.modules.MoviesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "dc310ae1f74743b9985a714c9e201984"
const val LANGUAGE = "en-US"

interface MoviesApi {

    @GET("now_playing/?api_key=$API_KEY&language=$LANGUAGE")
    fun getNowPlaying(@Query("page") page: Int): Observable<MoviesResponse>

    @GET("{movieId}?api_key=$API_KEY&language=$LANGUAGE&append_to_response=videos")
    fun getMovie(@Path("movieId") movieId: Long): Observable<Movie>
}