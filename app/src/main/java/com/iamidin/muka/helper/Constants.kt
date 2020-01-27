package com.iamidin.muka.helper

import com.iamidin.muka.BuildConfig

class Constants {

    class API {
        companion object {
            const val API_VERSION = "3"
            const val API_BASE_URL = "https://api.themoviedb.org/$API_VERSION/"
            const val API_KEY = BuildConfig.API_KEY
            const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
            const val IMAGE_SMALL_SIZE = "w342"
            const val IMAGE_MEDIUM_SIZE = "w500"
            const val IMAGE_LARGE_SIZE = "w780"

            const val COVER_IMAGE_SMALL_SIZE = "w300"
            const val COVER_IMAGE_MEDIUM_SIZE = "w780"
            const val COVER_IMAGE_LARGE_SIZE = "w1280"
        }
    }

}