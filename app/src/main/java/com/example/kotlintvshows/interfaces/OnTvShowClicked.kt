package com.example.kotlintvshows.interfaces

import com.example.kotlintvshows.tmdbAPI.model.TvShow

interface OnTvShowClicked {
    fun tvShowIntent(tvShow: TvShow)
}