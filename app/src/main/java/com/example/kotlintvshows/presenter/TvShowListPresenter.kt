package com.example.kotlintvshows.presenter

import android.content.Context
import com.example.kotlintvshows.R
import com.example.kotlintvshows.base.BasePresenter
import com.example.kotlintvshows.interfaces.TvShowListContract
import com.example.kotlintvshows.tmdbAPI.manager.TmdbManager
import com.example.kotlintvshows.tmdbAPI.model.TvShowsList

class TvShowListPresenter constructor(view: TvShowListContract.View, tmdbManager: TmdbManager):
    BasePresenter(), TvShowListContract.Presenter {

    var view: TvShowListContract.View? = null
    var tmdbManager: TmdbManager? = null

    init {
        this.view = view
        this.tmdbManager = tmdbManager
    }

    override fun fetchTvShowsData(context: Context,
                                  language: String,
                                  page: Int,
                                  requestType: String) {
        tmdbManager?.getTvShowsListData(object: TmdbManager.OnTvShowListDataFetched {
            override fun onSuccess(tvShowsListData: TvShowsList) {
                view?.showTvSHowListResponseDetails(tvShowsListData)
            }

            override fun onFailure() {
                view?.showToast(context.getString(R.string.error_no_internet))
            }
        }
        , language, page, requestType)
    }

    override fun fetchNextPageTvShowsData(context: Context,
                                          language: String,
                                          page: Int,
                                          requestType: String) {
        tmdbManager?.getTvShowsListData(object: TmdbManager.OnTvShowListDataFetched {
            override fun onSuccess(tvShowsListData: TvShowsList) {
                view?.loadNextResultsPage(tvShowsListData)
            }

            override fun onFailure() {
                view?.showToast(context.getString(R.string.error_no_internet))
            }
        }
            , language, page, requestType)
    }
}