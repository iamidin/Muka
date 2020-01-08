package com.iamidin.muka.viewmodel.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iamidin.muka.helper.Constants
import com.iamidin.muka.networking.NetworkService
import com.iamidin.muka.model.TvShow
import com.iamidin.muka.networking.response.TVResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowDiscoverViewModel : ViewModel() {

    private val _tvShowMutableLiveData: MutableLiveData<List<TvShow>> = MutableLiveData()
    val tvShowLiveData: LiveData<List<TvShow>>
        get() = _tvShowMutableLiveData


    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        discoverTV()
    }

    private fun discoverTV() {
        _isLoading.postValue(true)

        NetworkService.movieDBApi().discoverTV(Constants.API.API_KEY)
            .enqueue(object : Callback<TVResponse> {
                override fun onFailure(call: Call<TVResponse>, t: Throwable) {
                    _isLoading.postValue(false)
                }

                override fun onResponse(
                    call: Call<TVResponse>,
                    response: Response<TVResponse>
                ) {
                    _isLoading.postValue(false)

                    if (response.isSuccessful) {
                        response.body()?.let {
                            _tvShowMutableLiveData.postValue(it.results)
                        }
                    }
                }

            })
    }
}