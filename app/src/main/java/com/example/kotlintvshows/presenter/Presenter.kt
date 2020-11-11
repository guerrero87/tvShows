package com.example.kotlintvshows.presenter

import com.example.kotlintvshows.base.BasePresenter
import com.example.kotlintvshows.interfaces.Contract
import com.example.kotlintvshows.tmdbAPI.manager.TmdbManager
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.tmdbAPI.model.TvShowsList

class Presenter constructor(view: Contract.View, tmdbManager: TmdbManager):
    BasePresenter(), Contract.Presenter {

    var view: Contract.View? = null
    var tmdbManager: TmdbManager? = null

    init {
        this.view = view
        this.tmdbManager = tmdbManager
    }

    override fun fetchTvShowsData(language: String, page: Int, requestType: String) {
        tmdbManager?.getTvShowsData(object: TmdbManager.OnTvShowListDataFetched {
            override fun onSuccess(tvShowsListData: TvShowsList) {
                view?.showTvSHowListResponseDetails(tvShowsListData)
            }

            override fun onFailure() {
                view?.showError()
            }
        }
        , language, page, requestType)
    }

    override fun fetchNextPageTvShowsData(language: String, page: Int, requestType: String) {
        tmdbManager?.getTvShowsData(object: TmdbManager.OnTvShowListDataFetched {
            override fun onSuccess(tvShowsListData: TvShowsList) {
                view?.loadNextResultsPage(tvShowsListData)
            }

            override fun onFailure() {
                view?.showError()
            }
        }
            , language, page, requestType)
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