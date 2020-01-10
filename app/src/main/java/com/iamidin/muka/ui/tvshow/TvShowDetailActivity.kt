package com.iamidin.muka.ui.tvshow

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.iamidin.muka.R
import com.iamidin.muka.helper.Constants
import com.iamidin.muka.model.TvShow
import kotlinx.android.synthetic.main.activity_tv_show_detail.*

class TvShowDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TV_SHOW = "extra_tv_show"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        supportActionBar?.hide()

        val tvshow = intent.getParcelableExtra(EXTRA_TV_SHOW) as TvShow

        var year = ""

        val rating = tvshow.voteAverage / 2

        tvshow.firstAirDate.let {
            if (it.length > 4) year = "(${it.substring(0,4)})"
        }

        tv_tvshow_name.text = tvshow.name.toUpperCase() + " - " + year
        tv_tvshow_vote_count.text = tvshow.voteCount.toString()
        tv_tvshow_popularity.text = tvshow.popularity.toString()
        tv_tvshow_rating.text = tvshow.voteAverage.toString()
        rb_rating.rating = rating.toFloat()
        tv_tvshow_description.text = tvshow.overview

        loadCover(tvshow.backdropPath)
        loadPoster(tvshow.posterPath)

    }

    private fun loadCover(backdropPath: String) {
        Glide.with(this)
            .load(Constants.API.IMAGE_BASE_URL + Constants.API.COVER_IMAGE_MEDIUM_SIZE + backdropPath)
            .placeholder(R.drawable.ic_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(img_tvshow_cover_photo)
    }

    private fun loadPoster(posterPath: String) {
        Glide.with(this)
            .load(Constants.API.IMAGE_BASE_URL + Constants.API.COVER_IMAGE_SMALL_SIZE + posterPath)
            .placeholder(R.drawable.ic_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(img_tvshow_photo)
    }

}
