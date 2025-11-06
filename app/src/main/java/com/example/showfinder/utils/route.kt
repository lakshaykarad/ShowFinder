package com.example.showfinder.utils

// Routes for Navigation
sealed class Screen(val routes : String){
    object Home : Screen("home")
    object DetailScreem : Screen("details/{imdbId}"){
        fun creatRoute(imdbId:String) = "details/$imdbId"
    }
}