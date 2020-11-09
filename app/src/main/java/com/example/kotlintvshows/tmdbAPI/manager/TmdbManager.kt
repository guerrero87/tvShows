package com.example.kotlintvshows.tmdbAPI.manager

import com.example.kotlintvshows.tmdbAPI.interfaces.TmdbInterface
import com.example.kotlintvshows.tmdbAPI.model.TopTvShows
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.utils.Constants.Companion.TMDB_API_KEY
import com.example.kotlintvshows.utils.Constants.Companion.TMDB_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object TmdbManager {

    fun getClient(): TmdbInterface {

        return Retrofit.Builder()
            .baseUrl(TMDB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbInterface::class.java)
    }

    fun getTopTvShows(language: String, page: Int): Call<TopTvShows> {
        return getClient().getTopTvShows(TMDB_API_KEY, language, page)
    }
    fun getTvShow(tvShowId: Int, language: String): Call<TvShow> {
        return getClient().getTvShow(tvShowId, TMDB_API_KEY, language)
    }
}