package com.example.showfinder.data.model

import com.google.gson.annotations.SerializedName

// Data Class to search tv and movies
data class SearchItem (

    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String? = null,
    @SerializedName("type")
    val type : String? = null,
    @SerializedName("year")
    val year : Int? = null,
    @SerializedName("poster")
    val poster : String? = null,
    @SerializedName("tmdb_id")
    val tmbdID : Int? = null

)