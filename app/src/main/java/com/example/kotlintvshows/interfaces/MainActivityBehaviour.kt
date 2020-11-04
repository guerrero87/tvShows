package com.example.kotlintvshows.interfaces

import com.example.kotlintvshows.tmdbAPI.model.TvShow

interface MainActivityBehaviour {
    fun onTvShowPressed(tvShow: TvShow)
    fun onTvShowLongPressed()
}


