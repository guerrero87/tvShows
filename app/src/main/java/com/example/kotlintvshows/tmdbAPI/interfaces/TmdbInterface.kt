package com.example.kotlintvshows.tmdbAPI.interfaces

import com.example.kotlintvshows.tmdbAPI.model.TopTvShows
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbInterface {

    @GET("tv/popular")
    fun getTopTvShows(@Query("api_key") apiKey: String,
                      @Query("language") language: String,
                      @Query("page") page: Int)
            : Call<TopTvShows>

    @GET("tv/{id}")
    fun getTvShow(@Path("id") id: Int?,
                  @Query("api_key") apiKey: String,
                  @Query("language") language: String)
            : Call<TvShow>
}