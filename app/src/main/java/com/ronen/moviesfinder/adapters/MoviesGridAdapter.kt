package com.ronen.moviesfinder.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ronen.moviesfinder.R
import com.ronen.moviesfinder.modules.Movie


class MoviesGridAdapter(var movies: List<Movie>, val callback: MoviesGridClickListener)
    : RecyclerView.Adapter<MoviesGridAdapter.MoviesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.movie_grid_item_layout, parent, false)
        view.minimumHeight = parent?.measuredHeight!! / 4
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder?, position: Int) {
        holder?.updateItem(position)
    }

    inner class MoviesViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var image = itemView?.findViewById(R.id.movie_item_image) as ImageView

        fun updateItem(position: Int) {
            val movie = movies[position]

            itemView.setOnClickListener { callback.onMovieClicked(movie) }

            movie.posterUrl?.let {
                Glide.with(itemView.context).load(movie.getLargePosterImageUrl()).into(image)
            }
        }
    }
}


interface MoviesGridClickListener {
    fun onMovieClicked(movie: Movie)
}

