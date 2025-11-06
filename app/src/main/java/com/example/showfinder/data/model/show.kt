package com.example.showfinder.data.model

import com.google.gson.annotations.SerializedName

// Data class for TvShows
data class TvShow(
    val id : Int,
    val title : String,
    val year : Int,
    val type : String,
    @SerializedName("imdb_id")
    val imdb_id: String? = null,
    val poster : String,
    @SerializedName("release_date") val releaseDate : String?,
    @SerializedName("plot_overview") val overview : String?,
)