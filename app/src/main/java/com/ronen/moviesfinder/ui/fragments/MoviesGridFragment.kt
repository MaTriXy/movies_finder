package com.ronen.moviesfinder.ui.fragments

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ronen.moviesfinder.R
import com.ronen.moviesfinder.adapters.MoviesGridAdapter
import com.ronen.moviesfinder.adapters.MoviesGridClickListener
import com.ronen.moviesfinder.managers.ContentManager
import com.ronen.moviesfinder.modules.Movie
import com.ronen.moviesfinder.modules.MoviesResponse
import com.ronen.moviesfinder.ui.viewsComponents.EndlessRecyclerViewScrollListener

class MoviesGridFragment : BaseFragment(), MoviesGridClickListener, ContentManager.MoviesListener {

    private val moviesGridAdapter = MoviesGridAdapter(ArrayList<Movie>(), this)
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    companion object {
        fun newInstance(): MoviesGridFragment {
            return MoviesGridFragment()
        }
    }

    override fun onResume() {
        super.onResume()
        callback?.setToolbarTitle("Now playing")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.movies_grid_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwipeRefreshLayout(view)
        initMoviesGrid(view)
        fetchMovies()
    }



    private fun initSwipeRefreshLayout(view: View?) {
        swipeRefreshLayout = view?.findViewById(R.id.swipe_refresh_alyout) as SwipeRefreshLayout
        swipeRefreshLayout?.setOnRefreshListener { fetchMovies() }
    }

    private fun initMoviesGrid(view: View?) {
        val gridRv = view?.findViewById(R.id.movies_grid) as RecyclerView
        val gridLayoutManager = GridLayoutManager(context, if (isTablet()) 3 else 2)
        gridRv.layoutManager = gridLayoutManager
        gridRv.setHasFixedSize(true)
        gridRv.adapter = moviesGridAdapter
        gridRv.addOnScrollListener(object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                fetchMovies(page + 1)
            }

        })
    }

    private fun fetchMovies() {
        fetchMovies(1)
    }

    private fun fetchMovies(page: Int) {
        contentManager.getNowPlaying(page, this)
    }


    override fun onMovieClicked(movie: Movie) {
        callback?.onMovieClicked(movie)
    }

    override fun onMoviesReady(moviesResponse: MoviesResponse) {
        swipeRefreshLayout?.isRefreshing = false
        if (moviesResponse.isStoredData()) {
            moviesGridAdapter.movies = moviesResponse.movies
        } else {
            if (moviesResponse.page!! > 0) {
                moviesGridAdapter.movies += moviesResponse.movies
            } else {
                moviesGridAdapter.movies = moviesResponse.movies
            }
        }
        moviesGridAdapter.notifyDataSetChanged()
    }

    override fun onMoviesError() {
        swipeRefreshLayout?.isRefreshing = false
    }
}