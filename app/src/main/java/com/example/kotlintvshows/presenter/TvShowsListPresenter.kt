package com.example.kotlintvshows.presenter

import com.example.kotlintvshows.base.BasePresenter
import com.example.kotlintvshows.interfaces.TvShowsContract
import com.example.kotlintvshows.tmdbAPI.manager.TmdbManager
import com.example.kotlintvshows.tmdbAPI.model.TopTvShows

class TvShowsListPresenter constructor(view: TvShowsContract.View, tmdbManager: TmdbManager):
    BasePresenter(), TvShowsContract.Presenter {

    var view: TvShowsContract.View? = null
    var tmdbManager: TmdbManager? = null

    init {
        this.view = view
        this.tmdbManager = tmdbManager
    }

    override fun fetchTvShowsData(language: String, page: Int, requestType: String) {
        tmdbManager?.getTvShowsData(object: TmdbManager.OnDataFetched {
            override fun onSuccess(topTvShowsData: TopTvShows) {
                view?.showResponseDetails(topTvShowsData)
            }

            override fun onFailure() {
                view?.showError()
            }
        }
        , language, page, requestType)
    }

    override fun fetchNextPageTvShowsData(language: String, page: Int, requestType: String) {
        tmdbManager?.getTvShowsData(object: TmdbManager.OnDataFetched {
            override fun onSuccess(topTvShowsData: TopTvShows) {
                view?.loadNextResultsPage(topTvShowsData)
            }

            override fun onFailure() {
                view?.showError()
            }
        }
            , language, page, requestType)
    }
}