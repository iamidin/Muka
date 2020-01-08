package com.iamidin.muka.ui.movie

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.iamidin.muka.R
import com.iamidin.muka.helper.Constants
import com.iamidin.muka.model.Movie
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        supportActionBar?.hide()

        val movie = intent.getParcelableExtra(EXTRA_MOVIE) as Movie

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

        tv_movie_name.text = movie.title.toUpperCase() + " - " + year
        tv_movie_adult.text = adult
        tv_movie_vote_count.text = movie.voteCount.toString()
        tv_movie_popularity.text = movie.popularity.toString()
        tv_movie_rating.text = movie.voteAverage.toString()
        rb_rating.rating = rating.toFloat()
        tv_movie_description.text = movie.overview

        loadCover(movie.backdropPath)
        loadPoster(movie.posterPath)

    }

    private fun loadCover(backdropPath: String) {
        Glide.with(this)
            .load(Constants.API.IMAGE_BASE_URL + Constants.API.COVER_IMAGE_MEDIUM_SIZE + backdropPath)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(img_movie_cover_photo)
    }

    private fun loadPoster(posterPath: String) {
        Glide.with(this)
            .load(Constants.API.IMAGE_BASE_URL + Constants.API.COVER_IMAGE_SMALL_SIZE + posterPath)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(img_movie_photo)
    }

}
