package com.example.kotlintvshows.interfaces

import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.tmdbAPI.model.TvShowsList

interface TvShowListContract {

    interface View {
        fun fetchTvShowListDetails()
        fun showTvSHowListResponseDetails(tvshows: TvShowsList)
        fun loadNextResultsPage(tvshows: TvShowsList)
        fun onTvShowPressed(tvShow: TvShow)
        fun onTvShowLongPressed()
        fun showError()
    }

    interface Presenter {
        fun fetchTvShowsData(language: String, page: Int, requestType: String)
        fun fetchNextPageTvShowsData(language: String, page: Int, requestType: String)
        //TODO: NO TENGO UNA DUPLICACION DE METODOS DE ESTA MANERA??
        fun onTvShowPressed(tvShow: TvShow)
        fun onTvShowLongPressed()
    }
}