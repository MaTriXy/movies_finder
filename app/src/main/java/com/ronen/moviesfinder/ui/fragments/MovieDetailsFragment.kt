package com.ronen.moviesfinder.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ronen.moviesfinder.R
import com.ronen.moviesfinder.adapters.MoviesTrailersAdapter
import com.ronen.moviesfinder.managers.ContentManager
import com.ronen.moviesfinder.modules.Movie
import com.ronen.moviesfinder.modules.Video
import com.ronen.moviesfinder.ui.activities.YoutubeVideoActivity

class MovieDetailsFragment : BaseFragment(), ContentManager.MovieListener, MoviesTrailersAdapter.TrailerOnClickListener {

    var movie: Movie? = null
    var movieId: Long? = 0

    var title: TextView? = null
    var releaseDate: TextView? = null
    var description: TextView? = null
    var duration: TextView? = null
    var rating: TextView? = null
    var image: ImageView? = null
    var trailersAdapter: MoviesTrailersAdapter? = null


    companion object {
        val KEY_MOVIE_ID = "movie_id"
        fun newInstance(movieId: Long): MovieDetailsFragment {
            val bundle = Bundle()
            bundle.putSerializable(KEY_MOVIE_ID, movieId)

            val movieDetailsFragment = MovieDetailsFragment()
            movieDetailsFragment.arguments = bundle
            return movieDetailsFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = arguments?.getLong(KEY_MOVIE_ID)
    }

    override fun onResume() {
        super.onResume()
        if (!isTablet()) {
            callback?.setToolbarTitle("Movie details")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.movie_details_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view?.findViewById(R.id.movie_details_title) as TextView
        releaseDate = view.findViewById(R.id.movie_details_release_date) as TextView
        description = view.findViewById(R.id.movie_details_description) as TextView
        duration = view.findViewById(R.id.movie_details_duration) as TextView
        rating = view.findViewById(R.id.movie_details_rating) as TextView
        image = view.findViewById(R.id.movie_details_image) as ImageView

        val trailersRv = view.findViewById(R.id.movie_trailers_rv) as RecyclerView
        trailersRv.setHasFixedSize(true)
        trailersAdapter = MoviesTrailersAdapter(ArrayList(), this)
        trailersRv.layoutManager = LinearLayoutManager(activity)
        trailersRv.adapter = trailersAdapter

        contentManager.getMovie(movieId!!, this)
    }

    override fun onMovieReady(movie: Movie) {
        title?.text = movie.title
        releaseDate?.text = movie.releaseDate
        description?.text = movie.description
        duration?.text = getString(R.string.movie_duration, movie.duration)
        rating?.text = getString(R.string.movie_rating, movie.rating.toString())
        Glide.with(activity).load(movie.getSmallPosterImageUrl()).into(image)

        initTrailers(movie.videosList)
    }

    private fun initTrailers(videosList: ArrayList<Video>) {
        trailersAdapter?.videos = videosList
        trailersAdapter?.notifyDataSetChanged()
    }

    override fun onMovieError() {

    }

    override fun onTrailerClicked(youtubeVideoId: String) {
        startYoutubeActivity(youtubeVideoId)
    }

    private fun startYoutubeActivity(youtubeVideoId: String) {
        val intent = Intent(activity, YoutubeVideoActivity::class.java)
        intent.putExtra(YoutubeVideoActivity.KEY_VIDEO_ID, youtubeVideoId)
        startActivity(intent)
    }

}