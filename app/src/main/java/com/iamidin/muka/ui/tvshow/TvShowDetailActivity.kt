package com.iamidin.muka.ui.tvshow

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
import com.iamidin.muka.model.TvShow
import com.iamidin.muka.viewmodel.tvshow.TvShowDetailViewModel
import kotlinx.android.synthetic.main.activity_tv_show_detail.*
import java.util.*

class TvShowDetailActivity : AppCompatActivity() {

    private var isFavorite: Boolean = false
    private lateinit var tvShow: TvShow
    private lateinit var tvShowDetailViewModel: TvShowDetailViewModel

    
    companion object {
        const val EXTRA_TV_SHOW = "extra_tv_show"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)

        tvShowDetailViewModel = ViewModelProvider(this).get(TvShowDetailViewModel::class.java)
        
        btn_back.setOnClickListener {
            onBackPressed()
        }

        supportActionBar?.hide()

        tvShow = intent.getParcelableExtra(EXTRA_TV_SHOW) as TvShow

        var year = ""

        val rating = tvShow.voteAverage / 2

        tvShow.firstAirDate.let {
            if (it.length > 4) year = "(${it.substring(0,4)})"
        }

        tv_tvshow_name.text = """${tvShow.name.toUpperCase(Locale.getDefault())} - $year"""
        tv_tvshow_vote_count.text = tvShow.voteCount.toString()
        tv_tvshow_popularity.text = tvShow.popularity.toString()
        tv_tvshow_rating.text = tvShow.voteAverage.toString()
        rb_rating.rating = rating.toFloat()
        tv_tvshow_description.text = tvShow.overview

        loadCover(tvShow.backdropPath)
        loadPoster(tvShow.posterPath)

        iv_favorite.setOnClickListener {
            addFavorite()
        }

        tvShowDetailViewModel.getMovieById(tvShow.id)
        subscribeUI()

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

    private fun subscribeUI() {
        tvShowDetailViewModel.isFavorite.observe(this, Observer {
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
            tvShowDetailViewModel.removeFavorite(tvShow)
            toast(resources.getString(R.string.msg_delete_favorite))
        } else {
            tvShowDetailViewModel.addFavorite(tvShow)
            toast(resources.getString(R.string.msg_add_favorite))
        }
    }

    private fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

}
