package com.ronen.moviesfinder.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ronen.moviesfinder.R
import com.ronen.moviesfinder.modules.Video


class MoviesTrailersAdapter(var videos: List<Video>, val callback: TrailerOnClickListener)
    : RecyclerView.Adapter<MoviesTrailersAdapter.VideosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VideosViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.trailer_item_layout, parent, false)
        return VideosViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: VideosViewHolder?, position: Int) {
        holder?.updateData(position)
    }

    inner class VideosViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        var title = itemView?.findViewById(R.id.video_item_title) as TextView

        fun updateData(position: Int) {
            val video = videos[position]
            title.text = video.name
            itemView?.setOnClickListener { callback.onTrailerClicked(video.youtubeKey!!) }
        }
    }

    interface TrailerOnClickListener {
        fun onTrailerClicked(youtubeVideoId: String)
    }
}

