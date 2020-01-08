package com.iamidin.muka.viewmodel.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iamidin.muka.helper.Constants
import com.iamidin.muka.networking.NetworkService
import com.iamidin.muka.networking.response.MovieResponse
import com.iamidin.muka.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieNowPlayingViewModel : ViewModel() {

    private val _movieLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    val movieLiveData: LiveData<List<Movie>>
        get() = _movieLiveData


    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        nowPlayingMovie()
    }

    private fun nowPlayingMovie() {
        _isLoading.postValue(true)

        NetworkService.movieDBApi().nowPlayingMovie(Constants.API.API_KEY)
            .enqueue(object : Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    _isLoading.postValue(false)
                }

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    _isLoading.postValue(false)

                    if (response.isSuccessful) {
                        response.body()?.let {
                            _movieLiveData.postValue(it.results)
                        }
                    }
                }

            })
    }
}