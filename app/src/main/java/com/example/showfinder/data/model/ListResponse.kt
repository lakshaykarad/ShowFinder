package com.example.showfinder.data.model

import com.google.gson.annotations.SerializedName

// Use to map Json Data & Single (single.zip)

data class ListResponse<T>(
    val page : Int,
    val result : Int,
    @SerializedName("titles")
    val titles : List<T>?
)
