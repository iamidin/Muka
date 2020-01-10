package com.iamidin.muka.adapter.movie

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.iamidin.muka.R
import com.iamidin.muka.helper.Constants
import com.iamidin.muka.model.Movie
import com.iamidin.muka.ui.movie.MovieDetailActivity
import kotlinx.android.synthetic.main.item_cardview_movie_discover.view.*

class MovieDiscoverAdapter(private val movieList: List<Movie>) :
    RecyclerView.Adapter<MovieDiscoverAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cardview_movie_discover, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("DefaultLocale")
        fun bind(item: Movie) {
            with(itemView) {
                tv_movie_name.text = item.title.toUpperCase()
                Glide.with(itemView.context)
                    .load(Constants.API.IMAGE_BASE_URL + Constants.API.IMAGE_SMALL_SIZE + item.backdropPath)
                    .placeholder(R.drawable.ic_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_movie_poster)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, MovieDetailActivity::class.java)
                    intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, item)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}