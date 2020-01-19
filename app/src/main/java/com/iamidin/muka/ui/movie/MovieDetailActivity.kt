package com.iamidin.muka.ui.movie

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.iamidin.muka.R
import com.iamidin.muka.helper.Constants
import com.iamidin.muka.model.Movie
import com.iamidin.muka.viewmodel.movie.MovieDetailViewModel
import kotlinx.android.synthetic.main.activity_movie_detail.*
import java.util.*

class MovieDetailActivity : AppCompatActivity() {

    private var isFavorite: Boolean = false
    private lateinit var movie: Movie
    private lateinit var movieDetailViewModel: MovieDetailViewModel

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movieDetailViewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        supportActionBar?.hide()

        movie = intent.getParcelableExtra(EXTRA_MOVIE) as Movie

        var year = ""

        val rating = movie.voteAverage / 2

        val adult = if (movie.adult) {
            "18+"
        } else {
            "R"
        }

        movie.releaseDate.let {
            if (it.length > 4) year = "(${it.substring(0,4)})"
        }

        tv_movie_name.text = """${movie.title.toUpperCase(Locale.getDefault())} - $year"""
        tv_movie_adult.text = adult
        tv_movie_vote_count.text = movie.voteCount.toString()
        tv_movie_popularity.text = movie.popularity.toString()
        tv_movie_rating.text = movie.voteAverage.toString()
        rb_rating.rating = rating.toFloat()
        tv_movie_description.text = movie.overview

        loadCover(movie.backdropPath)
        loadPoster(movie.posterPath)

        iv_favorite.setOnClickListener {
            addFavorite()
        }

        movieDetailViewModel.getMovieById(movie.id)
        subscribeUI()

    }

    private fun loadCover(backdropPath: String) {
        Glide.with(this)
            .load(Constants.API.IMAGE_BASE_URL + Constants.API.COVER_IMAGE_MEDIUM_SIZE + backdropPath)
            .placeholder(R.drawable.ic_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(img_movie_cover_photo)
    }

    private fun loadPoster(posterPath: String) {
        Glide.with(this)
            .load(Constants.API.IMAGE_BASE_URL + Constants.API.COVER_IMAGE_SMALL_SIZE + posterPath)
            .placeholder(R.drawable.ic_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(img_movie_photo)
    }

    private fun subscribeUI() {
        movieDetailViewModel.isFavorite.observe(this, Observer {
            isFavorite = it
            applicationContext.let {

            }
            this.let { context ->
                if (isFavorite) {
                    iv_favorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_favorite_selected
                        )
                    )
                } else {
                    iv_favorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_favorite_unselect
                        )
                    )
                }
            }
        })
    }

    private fun Context.addFavorite() {
        if (isFavorite) {
            movieDetailViewModel.removeFavorite(movie)
            toast(resources.getString(R.string.msg_delete_favorite))
        } else {
            movieDetailViewModel.addFavorite(movie)
            toast(resources.getString(R.string.msg_add_favorite))
        }
    }

    private fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

}
