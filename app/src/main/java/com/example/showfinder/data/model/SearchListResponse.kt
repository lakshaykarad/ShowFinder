package com.example.showfinder.data.model

import com.google.gson.annotations.SerializedName

// Use to map Json Data & Single (single.zip)
data class SearchListResponse<T>(
    @SerializedName("title_results")
    val titles : List<T>?
)