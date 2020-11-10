package com.example.kotlintvshows.interfaces

import com.example.kotlintvshows.tmdbAPI.model.TopTvShows

interface TvShowsContract {

    interface View {
        fun fetchTvShowDetails()
        fun showResponseDetails(tvshows: TopTvShows)
        fun loadNextResultsPage(tvshows: TopTvShows)
        fun showError()
    }

    interface Presenter {
        fun fetchTvShowsData(language: String, page: Int, requestType: String)
        fun fetchNextPageTvShowsData(language: String, page: Int, requestType: String)
    }
}