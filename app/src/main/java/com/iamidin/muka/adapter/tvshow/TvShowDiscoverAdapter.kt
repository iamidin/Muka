package com.iamidin.muka.adapter.tvshow

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
import com.iamidin.muka.model.TvShow
import com.iamidin.muka.ui.tvshow.TvShowDetailActivity
import kotlinx.android.synthetic.main.item_cardview_tv_show_discover.view.*

class TvShowDiscoverAdapter(private val tvShowList: List<TvShow>) :
    RecyclerView.Adapter<TvShowDiscoverAdapter.TvShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cardview_tv_show_discover, parent, false)
        return TvShowViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tvShowList.size
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.bind(tvShowList[position])
    }

    inner class TvShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("DefaultLocale")
        fun bind(item: TvShow) {
            with(itemView) {
                tv_tvshow_name.text = item.name.toUpperCase()
                Glide.with(itemView.context)
                    .load(Constants.API.IMAGE_BASE_URL + Constants.API.IMAGE_SMALL_SIZE + item.backdropPath)
                    .placeholder(R.drawable.ic_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_tvshow_poster)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, TvShowDetailActivity::class.java)
                    intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, item)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}