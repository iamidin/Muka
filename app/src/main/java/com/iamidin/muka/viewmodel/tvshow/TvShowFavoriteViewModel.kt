package com.iamidin.muka.viewmodel.tvshow

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iamidin.muka.model.TvShow
import com.iamidin.muka.storage.MovieRoomDatabase
import kotlinx.coroutines.launch

class TvShowFavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val tvShowRoomDatabase: MovieRoomDatabase = MovieRoomDatabase.getDatabase(application)

    private val _favoriteTvShow: MutableLiveData<List<TvShow>> = MutableLiveData()
    val favoriteTvShow: LiveData<List<TvShow>>
        get() = _favoriteTvShow

    init {
        getFavoriteTvShow()
    }

    private fun getFavoriteTvShow() {
        viewModelScope.launch {
            val favoriteList = tvShowRoomDatabase.favoriteTvShowDao().getAllTvShow()
            _favoriteTvShow.postValue(favoriteList)
        }
    }

}