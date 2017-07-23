package com.ronen.moviesfinder.modules

import com.google.gson.annotations.SerializedName


class MoviesResponse() {

    var page: Int? = 0

    @SerializedName("total_pages")
    var totalPages: Int? = 0

    @SerializedName("results")
    var movies: List<Movie> = ArrayList()

    override fun toString(): String {
        return "MoviesResponse(page=$page, totalPages=$totalPages, videos=$movies)"
    }

    constructor(movies: List<Movie>) : this() {
        this.movies = movies
    }

    fun isStoredData(): Boolean {
        return totalPages == 0
    }

}