package com.iamidin.muka.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.iamidin.muka.R
import com.iamidin.muka.adapter.movie.MovieAdapter
import com.iamidin.muka.adapter.tvshow.TvShowAdapter
import com.iamidin.muka.model.Movie
import com.iamidin.muka.model.TvShow
import com.iamidin.muka.viewmodel.movie.MovieFavoriteViewModel
import com.iamidin.muka.viewmodel.tvshow.TvShowFavoriteViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class MovieFavoriteFragment : Fragment() {

    private lateinit var movieFavoriteViewModel: MovieFavoriteViewModel
    private lateinit var tvShowFavoriteViewModel: TvShowFavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeToolbarTitle(resources.getString(R.string.app_name).toUpperCase(Locale.getDefault()) + " " + resources.getString(R.string.title_favorite).toUpperCase(Locale.getDefault()))

        movieFavoriteViewModel = ViewModelProvider(this).get(MovieFavoriteViewModel::class.java)
        tvShowFavoriteViewModel = ViewModelProvider(this).get(TvShowFavoriteViewModel::class.java)

        initRecyclerView()
        subscribeUI()
    }

    private fun changeToolbarTitle(title: String?) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    private fun initRecyclerView() {
        rv_movies_favorite.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rv_movies_favorite.setHasFixedSize(true)

        rv_tv_show_favorite.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rv_tv_show_favorite.setHasFixedSize(true)
    }

    private fun subscribeUI() {
        movieFavoriteViewModel.favoriteMovies.observe(viewLifecycleOwner, Observer {
            showMovieInRecyclerView(it)
        })

        tvShowFavoriteViewModel.favoriteTvShow.observe(viewLifecycleOwner, Observer {
            showTvShowInRecyclerView(it)
        })
    }

    private fun showMovieInRecyclerView(movieList: List<Movie>) {
        val adapter = MovieAdapter(movieList)

        rv_movies_favorite.adapter = adapter
    }

    private fun showTvShowInRecyclerView(tvShowList: List<TvShow>) {
        val adapter = TvShowAdapter(tvShowList)

        rv_tv_show_favorite.adapter = adapter
    }

}
