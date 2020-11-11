package com.example.kotlintvshows.interfaces

import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.tmdbAPI.model.TvShowsList

interface Contract {

    interface View {
        fun fetchTvShowListDetails()
        fun fetchSingleTvShowDetails()
        fun showSingleTvShowResponseDetails(tvShow: TvShow)
        fun showTvSHowListResponseDetails(tvshows: TvShowsList)
        fun loadNextResultsPage(tvshows: TvShowsList)
        fun onTvShowPressed(tvShow: TvShow)
        fun onTvShowLongPressed()
        fun showError()
    }

    interface Presenter {
        fun fetchTvShowsData(language: String, page: Int, requestType: String)
        fun fetchNextPageTvShowsData(language: String, page: Int, requestType: String)
        fun fetchSingleTvShowData(tvShowId: Int, deviceLanguage: String)
        //TODO: NO TENGO UNA DUPLICACION DE METODOS DE ESTA MANERA??
        fun onTvShowPressed(tvShow: TvShow)
        fun onTvShowLongPressed()
    }
}