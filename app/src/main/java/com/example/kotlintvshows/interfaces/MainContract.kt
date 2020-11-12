package com.example.kotlintvshows.interfaces

import android.content.Context
import com.example.kotlintvshows.tmdbAPI.model.TvShow

interface MainContract {

    interface View {
        fun fetchSingleTvShowDetails()
        fun showSingleTvShowResponseDetails(tvShow: TvShow)
        fun onTvShowPressed(tvShow: TvShow)
        fun refreshRecycler(tvShow: TvShow)
        fun showToast(message: String)
    }

    interface Presenter {
        fun fetchSingleTvShowData(context: Context, tvShowId: Int, deviceLanguage: String)
    }
}