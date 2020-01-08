package com.iamidin.muka.networking.response


import com.iamidin.muka.model.TvShow
import com.google.gson.annotations.SerializedName

data class TVResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<TvShow>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)