package com.example.kotlintvshows.presenter

import android.content.Context
import com.example.kotlintvshows.R
import com.example.kotlintvshows.base.BasePresenter
import com.example.kotlintvshows.interfaces.MainContract
import com.example.kotlintvshows.interfaces.TvShowContract
import com.example.kotlintvshows.tmdbAPI.manager.TmdbManager
import com.example.kotlintvshows.tmdbAPI.model.TvShow

class TvShowPresenter constructor(view: TvShowContract.View, tmdbManager: TmdbManager):
    BasePresenter(), TvShowContract.Presenter {

    var view: TvShowContract.View? = null
    var tmdbManager: TmdbManager? = null

    init {
        this.view = view
        this.tmdbManager = tmdbManager
    }

    override fun fetchSingleTvShowData(context: Context, tvShowId: Int, deviceLanguage: String) {
        tmdbManager?.getTvShow(object: TmdbManager.OnSingleTvShowDataFetched {
            override fun onSuccess(tvShow: TvShow) {
                view?.showSingleTvShowResponseDetails(tvShow)
            }

            override fun onFailure() {
                view?.showToast(context.getString(R.string.error_no_internet))
            }
        },tvShowId, deviceLanguage)
    }
}