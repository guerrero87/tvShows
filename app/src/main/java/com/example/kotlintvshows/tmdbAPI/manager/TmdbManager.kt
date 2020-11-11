package com.example.kotlintvshows.tmdbAPI.manager

import android.util.Log
import com.example.kotlintvshows.tmdbAPI.interfaces.TmdbInterface
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.tmdbAPI.model.TvShowsList
import com.example.kotlintvshows.utils.Constants.Companion.TAG
import com.example.kotlintvshows.utils.Constants.Companion.TMDB_API_KEY
import com.example.kotlintvshows.utils.Constants.Companion.TMDB_URL
import com.example.kotlintvshows.utils.Constants.Companion.TOP_TV_SHOWS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object TmdbManager {

    interface OnTvShowListDataFetched {
        fun onSuccess(tvShowsListData: TvShowsList)
        fun onFailure()
    }

    interface OnSingleTvShowDataFetched {
        fun onSuccess(tvShow: TvShow)
        fun onFailure()
    }

    private fun getClient(): TmdbInterface {

        return Retrofit.Builder()
            .baseUrl(TMDB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbInterface::class.java)
    }

    fun getTvShowsData(listener: OnTvShowListDataFetched, language: String, page: Int, requestType: String) {

        val getData = when (requestType) {
                    TOP_TV_SHOWS -> getClient().getTopTvShows(TMDB_API_KEY, language, page)
                    //TODO: THINK DEFAULT CASE...
                    else -> getClient().getTopTvShows(TMDB_API_KEY, language, page)
        }

        getData.enqueue(object : Callback<TvShowsList>{
            override fun onResponse(call: Call<TvShowsList>, response: Response<TvShowsList>) {
                if (!response.isSuccessful){
                    listener.onFailure()
                    return
                }
                listener.onSuccess(response.body()!!)
            }

            override fun onFailure(call: Call<TvShowsList>, t: Throwable) {
                listener.onFailure()
                Log.e(TAG, "onFailure: " + t.cause)
            }
        })
    }

    fun getTvShow(listener: OnSingleTvShowDataFetched,tvShowId: Int, language: String) {
        val getData = getClient().getTvShow(tvShowId, TMDB_API_KEY, language)

        getData.enqueue(object : Callback<TvShow> {
            override fun onResponse(call: Call<TvShow>, response: Response<TvShow>) {
                if (!response.isSuccessful) {
                    listener.onFailure()
                }
                listener.onSuccess(response.body()!!)
            }

            override fun onFailure(call: Call<TvShow>, t: Throwable) {
                listener.onFailure()
                Log.e(TAG, "onFailure: " + t.cause)
            }
        })
    }
}