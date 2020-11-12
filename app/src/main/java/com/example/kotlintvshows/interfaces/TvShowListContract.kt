package com.example.kotlintvshows.interfaces

import android.content.Context
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.tmdbAPI.model.TvShowsList

interface TvShowListContract {

    interface View {
        fun fetchTvShowListDetails()
        fun showTvSHowListResponseDetails(tvshows: TvShowsList)
        fun loadNextResultsPage(tvshows: TvShowsList)
        fun onTvShowPressed(tvShow: TvShow)
        fun showToast(message: String)
    }

    interface Presenter {
        fun fetchTvShowsData(context: Context, language: String, page: Int, requestType: String)
        fun fetchNextPageTvShowsData(context: Context, language: String, page: Int, requestType: String)
    }
}