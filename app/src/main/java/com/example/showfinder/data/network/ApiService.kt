package com.example.showfinder.data.model.network

import com.example.showfinder.data.model.ListResponse
import com.example.showfinder.data.model.Movie
import com.example.showfinder.data.model.SearchItem
import com.example.showfinder.data.model.SearchListResponse
import com.example.showfinder.data.model.TvShow
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

// Inerface of movie, show and search mostly use in home screen and button toggle
interface ApiService {

    @GET("list-titles/")
    fun getMovie(
        @Query("apiKey") apiKey : String = ApiConstants.API_KEY,
        @Query("types") type : String = "movie",
        @Query("limit") limit : Int = 100
     ) : Single<ListResponse<Movie>>

    @GET("list-titles/")
    fun getTvShow(
        @Query("apiKey") apiKey : String = ApiConstants.API_KEY,
        @Query("types") type : String = "tv_series",
        @Query("limit") limit : Int = 100
    ) : Single<ListResponse<TvShow>>

    @GET("search/")
    fun searchMedia(
        @Query("apiKey") apiKey : String = ApiConstants.API_KEY,
        @Query("search_field") searchField : String = "name",
        @Query("search_value") query : String,
        @Query("limit") limit : Int = 100
    ) : Single<SearchListResponse<SearchItem>>

}