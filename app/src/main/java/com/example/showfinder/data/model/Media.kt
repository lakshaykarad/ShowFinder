package com.example.showfinder.data.model

// This Data Class use in Detail Screen
data class Media(
    val id: Int = 0,
    val title: String,
    val year: Int?,
    val type: String,
    val imdb_id: String?,
    val poster: String?,
    val releaseDate: String?,
    val overview: String?,
    val runtime: String? = null,
    val genre: String? = null,
    val director: String? = null,
    val writer: String? = null,
    val actors: String? = null,
    val rated: String? = null,
    val awards: String? = null
)