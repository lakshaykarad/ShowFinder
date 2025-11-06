package com.example.showfinder.data.network

import com.example.showfinder.data.model.network.ApiConstants
import com.google.gson.annotations.SerializedName
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

data class OmdbdResponse(
    val Title: String? = null,
    val Year: String? = null,
    val Rated: String? = null,
    val Released: String? = null,
    val Runtime: String? = null,
    val Genre: String? = null,
    val Director: String? = null,
    val Writer: String? = null,
    val Actors: String? = null,
    val Plot: String? = null,
    val Language: String? = null,
    val Country: String? = null,
    val Awards: String? = null,
    val Poster: String? = null,
    @SerializedName("imdbID")
    val imdbID: String? = null,
    val Type: String? = null,
    val Response: String? = null
)

interface OmdbApiService {
    @GET(".")
    fun getMovieByImdbID(
        @Query("i") imdbID: String,
        @Query("apikey") apiKey: String = ApiConstants.OMDb_API_KEY
    ): Single<OmdbdResponse>
}
