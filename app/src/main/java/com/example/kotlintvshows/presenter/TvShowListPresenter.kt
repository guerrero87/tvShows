package com.example.kotlintvshows.presenter

import com.example.kotlintvshows.base.BasePresenter
import com.example.kotlintvshows.interfaces.TvShowListContract
import com.example.kotlintvshows.tmdbAPI.manager.TmdbManager
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.tmdbAPI.model.TvShowsList

class TvShowListPresenter constructor(view: TvShowListContract.View, tmdbManager: TmdbManager):
    BasePresenter(), TvShowListContract.Presenter {

    var view: TvShowListContract.View? = null
    var tmdbManager: TmdbManager? = null

    init {
        this.view = view
        this.tmdbManager = tmdbManager
    }

    override fun fetchTvShowsData(language: String, page: Int, requestType: String) {
        tmdbManager?.getTvShowsListData(object: TmdbManager.OnTvShowListDataFetched {
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
        tmdbManager?.getTvShowsListData(object: TmdbManager.OnTvShowListDataFetched {
            override fun onSuccess(tvShowsListData: TvShowsList) {
                view?.loadNextResultsPage(tvShowsListData)
            }

            override fun onFailure() {
                view?.showError()
            }
        }
            , language, page, requestType)
    }

    //TODO: DEBO PONER ESTE TIPO DE METODOS EN EL PRESENTER?
    override fun onTvShowPressed(tvShow: TvShow) {
        view?.onTvShowPressed(tvShow)
    }

    override fun onTvShowLongPressed() {
        view?.onTvShowLongPressed()
    }
}