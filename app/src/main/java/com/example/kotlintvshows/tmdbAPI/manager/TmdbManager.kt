package com.example.kotlintvshows.tmdbAPI.manager

import com.example.kotlintvshows.tmdbAPI.interfaces.TmdbInterface
import com.example.kotlintvshows.tmdbAPI.model.TopTvShows
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object TmdbManager {
    val API_KEY = "9e2daa9c345ee62adba8d6e48e1632bb"

    private val TMDB_URL = "https://api.themoviedb.org/3/"

    fun getClient(): TmdbInterface {

        return Retrofit.Builder()
            .baseUrl(TMDB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbInterface::class.java)
    }

    fun getTopTvShows(language: String, page: Int): Call<TopTvShows> {
        return getClient().getTopTvShows(API_KEY, language, page)
    }
    fun getTvShow(tvShowId: Int, language: String): Call<TvShow> {
        return getClient().getTvShow(tvShowId, API_KEY, language)
    }
}