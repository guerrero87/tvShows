package com.example.kotlintvshows.interfaces

import android.content.Context
import com.example.kotlintvshows.tmdbAPI.model.TvShow

interface MainContract {

    interface View {
        fun fetchFavTvShowsDetails() //pasar a privado
        fun showSingleTvShowResponseDetails(tvShow: TvShow)
        fun onTvShowPressed(tvShowId: Int)
        fun refreshRecycler(tvShow: TvShow)
        fun showToast(message: String)
    }

    interface Presenter {
        fun fetchSingleTvShowData(context: Context, tvShowId: Int, deviceLanguage: String)
    }
}