package com.example.kotlintvshows.presenter

import com.example.kotlintvshows.base.BasePresenter
import com.example.kotlintvshows.interfaces.MainContract
import com.example.kotlintvshows.tmdbAPI.manager.TmdbManager
import com.example.kotlintvshows.tmdbAPI.model.TvShow

class MainPresenter constructor(view: MainContract.View, tmdbManager: TmdbManager):
    BasePresenter(), MainContract.Presenter {

    var view: MainContract.View? = null
    var tmdbManager: TmdbManager? = null

    init {
        this.view = view
        this.tmdbManager = tmdbManager
    }

    override fun fetchSingleTvShowData(tvShowId: Int, deviceLanguage: String) {
        tmdbManager?.getTvShow(object: TmdbManager.OnSingleTvShowDataFetched {
            override fun onSuccess(tvShow: TvShow) {
                view?.showSingleTvShowResponseDetails(tvShow)
            }

            override fun onFailure() {
                view?.showError()
            }

        },tvShowId, deviceLanguage)
    }

    //TODO: DEBO PONER ESTE TIPO DE METODOS EN EL PRESENTER?
    override fun onTvShowPressed(tvShow: TvShow) {
        view?.onTvShowPressed(tvShow)
    }

    override fun onTvShowLongPressed() {
        view?.onTvShowLongPressed()
    }
}