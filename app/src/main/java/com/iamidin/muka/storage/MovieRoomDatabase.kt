package com.iamidin.muka.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iamidin.muka.helper.Constants.DATABASE.Companion.DB_NAME
import com.iamidin.muka.helper.Constants.DATABASE.Companion.DB_VERSION
import com.iamidin.muka.model.Movie
import com.iamidin.muka.model.TvShow
import com.iamidin.muka.storage.dao.FavoriteMovieDao
import com.iamidin.muka.storage.dao.FavoriteTvShowDao

@Database(entities = [Movie::class, TvShow::class], version = DB_VERSION, exportSchema = false)
abstract class MovieRoomDatabase : RoomDatabase() {

    abstract fun favoriteMovieDao(): FavoriteMovieDao

    abstract fun favoriteTvShowDao(): FavoriteTvShowDao

    companion object {
        @Volatile
        private var INSTANCE: MovieRoomDatabase? = null

        fun getDatabase(context: Context): MovieRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieRoomDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}