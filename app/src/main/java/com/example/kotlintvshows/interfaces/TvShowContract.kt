package com.example.kotlintvshows.interfaces

import android.content.Context
import com.example.kotlintvshows.tmdbAPI.model.TvShow

interface TvShowContract {

    interface View {
        fun fetchFavTvShowsDetails()
        fun showSingleTvShowResponseDetails(tvShow: TvShow)
        fun setSubscribeBtnUnClicked()
        fun setSubscribeBtnClicked()
        fun isSubscribeBtnEnabled(boolean: Boolean)
        fun showToast(message: String)
    }

    interface Presenter {
        fun fetchSingleTvShowData(context: Context, tvShowId: Int, deviceLanguage: String)
    }
}