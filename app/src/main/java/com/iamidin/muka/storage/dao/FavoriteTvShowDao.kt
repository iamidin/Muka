package com.iamidin.muka.storage.dao

import androidx.room.*
import com.iamidin.muka.model.TvShow

@Dao
interface FavoriteTvShowDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tvShow: TvShow)

    @Delete
    suspend fun delete(tvShow: TvShow)

    @Query("SELECT * FROM favorite_tv_show ORDER BY first_air_date DESC")
    suspend fun getAllTvShow(): List<TvShow>

    @Query("SELECT * FROM favorite_tv_show where id=:id")
    suspend fun getTvShowById(id: Int): TvShow
}