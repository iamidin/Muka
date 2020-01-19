package com.iamidin.muka.viewmodel.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iamidin.muka.model.Movie
import com.iamidin.muka.storage.MovieRoomDatabase
import kotlinx.coroutines.launch

class MovieFavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRoomDatabase: MovieRoomDatabase = MovieRoomDatabase.getDatabase(application)

    private val _favoriteMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val favoriteMovies: LiveData<List<Movie>>
        get() = _favoriteMovies

    init {
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch {
            val favoriteList = movieRoomDatabase.favoriteMovieDao().getAllMovie()
            _favoriteMovies.postValue(favoriteList)
        }
    }

}