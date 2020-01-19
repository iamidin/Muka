package com.iamidin.muka.storage.dao

import androidx.room.*
import com.iamidin.muka.model.Movie

@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteMovie: Movie)

    @Delete
    suspend fun delete(favoriteMovie: Movie)

    @Query("SELECT * FROM favorite_movie ORDER BY release_date DESC")
    suspend fun getAllMovie(): List<Movie>

    @Query("SELECT * FROM favorite_movie where id=:id")
    suspend fun getMovieById(id: Int): Movie
}