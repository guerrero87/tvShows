package com.example.kotlintvshows.interfaces

import com.example.kotlintvshows.tmdbAPI.model.TopTvShows
import com.example.kotlintvshows.tmdbAPI.model.TvShow

interface TvShowsContract {

    interface View {
        fun fetchTvShowDetails()
        fun showResponseDetails(tvshows: TopTvShows)
        fun showError()
    }

    interface Presenter {
        fun fetchTvShowsData(language: String, page: Int, requestType: String)
    }
}