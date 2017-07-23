package com.ronen.moviesfinder.modules

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "videos")
class Video(parentMovieId: Long?, jsonObject: JsonObject?) : Serializable {

    @PrimaryKey
    var videoId: String? = jsonObject?.get("id")?.asString

    @ColumnInfo(name = "movie_id")
    var movieId: Long? = parentMovieId

    @SerializedName("key")
    var youtubeKey: String? = jsonObject?.get("key")?.asString

    var name: String? = jsonObject?.get("name")?.asString

    constructor() : this(0, null)
}