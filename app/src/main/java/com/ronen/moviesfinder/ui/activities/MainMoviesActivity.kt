package com.ronen.moviesfinder.ui.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.ronen.moviesfinder.R
import com.ronen.moviesfinder.managers.ContentManager
import com.ronen.moviesfinder.modules.Movie
import com.ronen.moviesfinder.ui.fragments.BaseFragment
import com.ronen.moviesfinder.ui.fragments.MovieDetailsFragment
import com.ronen.moviesfinder.ui.fragments.MoviesGridFragment
import javax.inject.Inject

class MainMoviesActivity : AppCompatActivity(), BaseFragment.ActivityFragmentInteractionListener {

    @Inject
    lateinit var contentManager: ContentManager

    var mIsTablet: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_layout)

        mIsTablet = resources.getBoolean(R.bool.isTablet)

        initOrientation()
        initLayout()
    }

    private fun initOrientation() {
        if (isTablet()) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    private fun initLayout() {
        setSupportActionBar(findViewById(R.id.main_toolbar) as Toolbar)
        supportFragmentManager.beginTransaction()
                .add(R.id.main_container,
                        MoviesGridFragment.newInstance(),
                        MoviesGridFragment::class.simpleName)
                .commit()
    }

    override fun onMovieClicked(movie: Movie) {
        if (isTablet()) {
            if (supportFragmentManager.backStackEntryCount == 0) {
                supportFragmentManager.beginTransaction()
                        .add(R.id.movie_details_container,
                                MovieDetailsFragment.newInstance(movie.id!!),
                                MovieDetailsFragment::class.simpleName)
                        .commit()
            } else {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.movie_details_container,
                                MovieDetailsFragment.newInstance(movie.id!!),
                                MovieDetailsFragment::class.simpleName)
                        .commit()
            }
        } else {
            supportFragmentManager.beginTransaction()
                    .add(R.id.main_container,
                            MovieDetailsFragment.newInstance(movie.id!!),
                            MovieDetailsFragment::class.simpleName)
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun isTablet(): Boolean {
        return mIsTablet
    }
}