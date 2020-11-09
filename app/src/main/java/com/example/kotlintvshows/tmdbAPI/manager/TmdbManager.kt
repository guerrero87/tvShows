package com.example.kotlintvshows.tmdbAPI.manager

import android.util.Log
import com.example.kotlintvshows.tmdbAPI.interfaces.TmdbInterface
import com.example.kotlintvshows.tmdbAPI.model.TopTvShows
import com.example.kotlintvshows.tmdbAPI.model.TvShow
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

    interface OnDataFetched {
        fun onSuccess(topTvShowsData: TopTvShows)
        fun onFailure()
    }

    fun getClient(): TmdbInterface {

        return Retrofit.Builder()
            .baseUrl(TMDB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbInterface::class.java)
    }

    fun getTvShowsData(listener: OnDataFetched, language: String, page: Int, requestType: String) {

        val getData = when (requestType) {
                    TOP_TV_SHOWS -> getClient().getTopTvShows(TMDB_API_KEY, language, page)
                    //TODO: THINK DEFAULT CASE...
                    else -> getClient().getTopTvShows(TMDB_API_KEY, language, page)
        }

        getData.enqueue(object : Callback<TopTvShows>{
            override fun onResponse(call: Call<TopTvShows>, response: Response<TopTvShows>) {
                if (!response.isSuccessful){
                    listener.onFailure()
                    return
                }
                listener.onSuccess(response.body()!!)
            }

            override fun onFailure(call: Call<TopTvShows>, t: Throwable) {
                listener.onFailure()
                Log.e(TAG, "onFailure: " + t.cause)
            }
        })
    }

    fun getTvShow(tvShowId: Int, language: String): Call<TvShow> {
        return getClient().getTvShow(tvShowId, TMDB_API_KEY, language)
    }
}