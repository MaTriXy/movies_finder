package com.ronen.moviesfinder.modules

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import java.io.Serializable

const val BASE_LARGE_IMAGES_URL = "https://image.tmdb.org/t/p/w500"
const val BASE_SMALL_IMAGES_URL = "https://image.tmdb.org/t/p/w300"

@Entity(tableName = "movies")
class Movie : Serializable {

    @PrimaryKey
    var id: Long? = 0

    var title: String? = null

    @SerializedName("poster_path")
    var posterUrl: String? = null

    @SerializedName("vote_average")
    var rating: Float? = 0f

    @SerializedName("vote_count")
    var voteCount: Int? = 0

    @SerializedName("overview")
    var description: String? = null

    @SerializedName("release_date")
    var releaseDate: String? = null

    @SerializedName("runtime")
    var duration: Int? = 0

    @SerializedName("videos")
    @Ignore
    var videoJson: JsonObject? = null

    @Ignore
    var videosList = ArrayList<Video>()

    fun getLargePosterImageUrl(): String {
        return BASE_LARGE_IMAGES_URL + posterUrl
    }

    fun getSmallPosterImageUrl(): String {
        return BASE_SMALL_IMAGES_URL + posterUrl
    }

    fun parseVideos(videosJsonArray: JsonArray?) {
        videosJsonArray?.let {
            (0..videosJsonArray.size() - 1).mapTo(videosList) {
                Video(id, videosJsonArray.get(it).asJsonObject)
            }
        }

    }
}