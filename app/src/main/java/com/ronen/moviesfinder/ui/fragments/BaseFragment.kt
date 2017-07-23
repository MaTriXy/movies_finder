package com.ronen.moviesfinder.ui.fragments

import android.content.Context
import android.support.v4.app.Fragment
import com.ronen.moviesfinder.MoviesFinderApp
import com.ronen.moviesfinder.managers.ContentManager
import com.ronen.moviesfinder.modules.Movie
import javax.inject.Inject

abstract class BaseFragment: Fragment() {

    @Inject
    lateinit var contentManager: ContentManager
    var callback: ActivityFragmentInteractionListener? = null
    var dismissListener: FragmentDismissListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as ActivityFragmentInteractionListener
        MoviesFinderApp.appComponent.inject(this)
    }

    interface ActivityFragmentInteractionListener {
        fun onMovieClicked(movie: Movie)
        fun setToolbarTitle(title: String)
        fun isTablet(): Boolean
    }

    override fun onPause() {
        super.onPause()
        dismissListener?.onFragmentDismissed(this::class.simpleName.toString())
    }

    fun isTablet(): Boolean {
        return callback?.isTablet()!!
    }

    interface FragmentDismissListener {
        fun onFragmentDismissed(fragmentName: String)
    }

}