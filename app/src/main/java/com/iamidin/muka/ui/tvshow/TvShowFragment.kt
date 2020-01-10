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
            showLoading(it, "discover")
        })

        discoverViewModel.tvShowLiveData.observe(viewLifecycleOwner, Observer {
            showTvShowInRecyclerView(it, "discover")
        })

        topRatedViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            showLoading(it, "top_rated")
        })

        topRatedViewModel.tvShowLiveData.observe(viewLifecycleOwner, Observer {
            showTvShowInRecyclerView(it, "top_rated")
        })

        popularViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            showLoading(it, "popular")
        })

        popularViewModel.tvShowLiveData.observe(viewLifecycleOwner, Observer {
            showTvShowInRecyclerView(it, "popular")
        })
    }

    private fun showLoading(state: Boolean, shimmerId: String) {
        if (state) {
            when (shimmerId) {
                "discover" -> {
                    shimmer_layout_discover.visibility = View.VISIBLE
                    shimmer_layout_discover.startShimmer()
                    tv_title_discover.visibility = View.INVISIBLE
                }

                "top_rated" -> {
                    shimmer_layout_toprated.visibility = View.VISIBLE
                    shimmer_layout_toprated.startShimmer()
                    tv_title_top_rated.visibility = View.INVISIBLE
                }

                "popular" -> {
                    shimmer_layout_popular.visibility = View.VISIBLE
                    shimmer_layout_popular.startShimmer()
                    tv_title_popular.visibility = View.INVISIBLE
                }
            }
        } else {
            when (shimmerId) {
                "discover" -> {
                    shimmer_layout_discover.stopShimmer()
                    shimmer_layout_discover.visibility = View.INVISIBLE
                    tv_title_discover.visibility = View.VISIBLE
                }

                "top_rated" -> {
                    shimmer_layout_toprated.stopShimmer()
                    shimmer_layout_toprated.visibility = View.INVISIBLE
                    tv_title_top_rated.visibility = View.VISIBLE
                }

                "popular" -> {
                    shimmer_layout_popular.stopShimmer()
                    shimmer_layout_popular.visibility = View.INVISIBLE
                    tv_title_popular.visibility = View.VISIBLE
                }
            }
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