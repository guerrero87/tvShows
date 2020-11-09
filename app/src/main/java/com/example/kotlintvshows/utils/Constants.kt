package com.example.kotlintvshows.utils

import com.example.kotlintvshows.BuildConfig

class Constants {
    companion object {
        /**
        ATTENTION: REPLACE API_KEY WITH YOUR OWN PERSONAL API KEY VALUE
         **/
        const val TMDB_API_KEY = BuildConfig.TMDB_API_KEY
        const val FILE_NAME = "favTvShows1.json"
        const val TMDB_URL = "https://api.themoviedb.org/3/"
        const val SUBSCRIBE = "SUBSCRIBE"
        const val ADDED = "ADDED"
        const val TAG = "MY_TAG"
        const val GRID_LAYOUT_COLUMN_NUMBER = 3

        //REQUEST TYPES
        const val TOP_TV_SHOWS = "TopTvShows"
    }
}
