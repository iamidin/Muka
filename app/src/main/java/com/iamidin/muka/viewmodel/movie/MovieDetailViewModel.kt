package com.iamidin.muka.viewmodel.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iamidin.muka.model.Movie
import com.iamidin.muka.storage.MovieRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRoomDatabase = MovieRoomDatabase.getDatabase(application)

    private val _isFavorite = MutableLiveData<Boolean>(false)
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    fun addFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRoomDatabase.favoriteMovieDao().insert(movie)
            _isFavorite.postValue(true)
        }
    }

    fun removeFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRoomDatabase.favoriteMovieDao().delete(movie)
            _isFavorite.postValue(false)
        }
    }

    fun getMovieById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movie: Movie? = movieRoomDatabase.favoriteMovieDao().getMovieById(id)
            if (movie != null) {
                _isFavorite.postValue(true)
            } else {
                _isFavorite.postValue(false)
            }
        }
    }

}