package com.example.kotlintvshows.interfaces

import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.tmdbAPI.model.TvShowsList

interface MainContract {

    interface View {
        fun fetchSingleTvShowDetails()
        fun showSingleTvShowResponseDetails(tvShow: TvShow)
        fun onTvShowPressed(tvShow: TvShow)
        fun onTvShowLongPressed()
        fun showError()
    }

    interface Presenter {
        fun fetchSingleTvShowData(tvShowId: Int, deviceLanguage: String)
        //TODO: NO TENGO UNA DUPLICACION DE METODOS DE ESTA MANERA??
        fun onTvShowPressed(tvShow: TvShow)
        fun onTvShowLongPressed()
    }
}