package com.example.showfinder.utils

sealed class Resource<out T>{
    object Loading : Resource<Nothing>()
    data class Error (val message : String) : Resource<Nothing>()
    data class Success<T>(val data : T) : Resource<T>()
}