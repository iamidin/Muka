package com.iamidin.muka.ui.tvshow

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
import com.iamidin.muka.adapter.tvshow.TvShowDiscoverAdapter
import com.iamidin.muka.adapter.tvshow.TvShowPopularAdapter
import com.iamidin.muka.adapter.tvshow.TvShowTopRatedAdapter
import com.iamidin.muka.model.TvShow
import com.iamidin.muka.viewmodel.tvshow.TvShowDiscoverViewModel
import com.iamidin.muka.viewmodel.tvshow.TvShowPopularViewModel
import com.iamidin.muka.viewmodel.tvshow.TvShowTopRatedViewModel
import kotlinx.android.synthetic.main.fragment_tv_show.*

class TvShowFragment : Fragment() {

    private lateinit var discoverViewModel: TvShowDiscoverViewModel
    private lateinit var topRatedViewModel: TvShowTopRatedViewModel
    private lateinit var popularViewModel: TvShowPopularViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeToolbarTitle(resources.getString(R.string.app_name) + " " + resources.getString(R.string.title_tv_show))

        discoverViewModel = ViewModelProvider(this).get(TvShowDiscoverViewModel::class.java)
        topRatedViewModel = ViewModelProvider(this).get(TvShowTopRatedViewModel::class.java)
        popularViewModel = ViewModelProvider(this).get(TvShowPopularViewModel::class.java)

        initRecyclerView()

        subscribeUI()
    }

    private fun changeToolbarTitle(title: String?) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    private fun initRecyclerView() {
        rv_tv_show_discover.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rv_tv_show_discover.setHasFixedSize(true)

        rv_tv_show_top_rated.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rv_tv_show_top_rated.setHasFixedSize(true)

        rv_tv_show_popular.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rv_tv_show_popular.setHasFixedSize(true)
    }

    private fun subscribeUI() {
        discoverViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            showLoading(it)
        })

        discoverViewModel.tvShowLiveData.observe(viewLifecycleOwner, Observer {
            showTvShowInRecyclerView(it, "discover")
        })

        topRatedViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            showLoading(it)
        })

        topRatedViewModel.tvShowLiveData.observe(viewLifecycleOwner, Observer {
            showTvShowInRecyclerView(it, "top_rated")
        })

        popularViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            showLoading(it)
        })

        popularViewModel.tvShowLiveData.observe(viewLifecycleOwner, Observer {
            showTvShowInRecyclerView(it, "popular")
        })
    }

    private fun showLoading(state: Boolean) {
        progress_bar.visibility = if (state) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }

    }

    private fun showTvShowInRecyclerView(
        tvList: List<TvShow>,
        to: String
    ) {
        when(to) {
            "discover" -> {
                val adapter = TvShowDiscoverAdapter(tvList)
                rv_tv_show_discover.adapter = adapter

            }

            "top_rated" -> {
                val adapter = TvShowTopRatedAdapter(tvList)
                rv_tv_show_top_rated.adapter = adapter

            }
            "popular" -> {
                val adapter = TvShowPopularAdapter(tvList)
                rv_tv_show_popular.adapter = adapter

            }
        }
    }
}