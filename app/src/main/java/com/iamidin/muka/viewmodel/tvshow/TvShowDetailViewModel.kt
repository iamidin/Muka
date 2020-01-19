package com.iamidin.muka.viewmodel.tvshow

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iamidin.muka.model.TvShow
import com.iamidin.muka.storage.MovieRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TvShowDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRoomDatabase = MovieRoomDatabase.getDatabase(application)

    private val _isFavorite = MutableLiveData<Boolean>(false)
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    fun addFavorite(tvShow: TvShow) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRoomDatabase.favoriteTvShowDao().insert(tvShow)
            _isFavorite.postValue(true)
        }
    }

    fun removeFavorite(tvShow: TvShow) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRoomDatabase.favoriteTvShowDao().delete(tvShow)
            _isFavorite.postValue(false)
        }
    }

    fun getMovieById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val tvShow: TvShow? = movieRoomDatabase.favoriteTvShowDao().getTvShowById(id)
            if (tvShow != null) {
                _isFavorite.postValue(true)
            } else {
                _isFavorite.postValue(false)
            }
        }
    }

}