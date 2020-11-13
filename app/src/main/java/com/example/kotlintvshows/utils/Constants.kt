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
        const val GRID_LAYOUT_COLUMN_NUMBER = 3
        //TAGS
        const val TAG = "MY_TAG"
        const val REQUEST_TYPE = "REQUEST_TYPE"
        const val ORIGIN = "ORIGIN"
        const val TV_SHOW = "TV_SHOW"
        //INTENT ORIGIN TYPES
        const val MAIN_ACTIVITY = "Main Activity"
        const val LIST_ACTIVITY = "List Activity"
        //REQUEST TYPES
        const val POPULAR_TV_SHOWS = "Popular TvShows"
        const val TOP_TV_SHOWS = "Top TvShows"
    }
}
