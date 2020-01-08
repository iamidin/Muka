package com.iamidin.muka.networking

import com.iamidin.muka.helper.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {

    private fun retrofitService(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(Constants.API.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun movieDBApi(): MovieDBApi = retrofitService().create(MovieDBApi::class.java)
}