package com.example.kotlintvshows.utils

import com.example.kotlintvshows.BuildConfig

class Constants {
    companion object {
        /**
        ATTENTION: REPLACE API_KEY WITH YOUR OWN PERSONAL API KEY VALUE
         **/
        const val TMDB_API_KEY = BuildConfig.TMDB_API_KEY
        const val fileName = "favTvShows1.json"
        const val TMDB_URL = "https://api.themoviedb.org/3/"
        const val suscribe = "SUBSCRIBE"
        const val added = "ADDED"
        const val gridLayoutColumnNumber = 3
    }
}
