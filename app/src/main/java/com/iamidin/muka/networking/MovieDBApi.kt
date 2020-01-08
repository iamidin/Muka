package com.iamidin.muka.networking

import com.iamidin.muka.networking.response.MovieResponse
import com.iamidin.muka.networking.response.TVResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBApi {

    @GET("discover/movie")
    fun discoverMovie(
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>

    @GET("movie/now_playing")
    fun nowPlayingMovie(
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>

    @GET("movie/popular")
    fun popularMovie(
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>

    @GET("discover/tv")
    fun discoverTV(
        @Query("api_key") apiKey: String
    ): Call<TVResponse>

    @GET("tv/top_rated")
    fun topRatedTV(
        @Query("api_key") apiKey: String
    ): Call<TVResponse>

    @GET("tv/popular")
    fun popularTV(
        @Query("api_key") apiKey: String
    ): Call<TVResponse>
}