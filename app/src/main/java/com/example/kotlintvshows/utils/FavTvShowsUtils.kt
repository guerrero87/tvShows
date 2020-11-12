package com.example.kotlintvshows.utils

import android.content.Context
import com.google.gson.Gson

fun addTvShowToFavourites(context: Context, tvShowId: Int) {
    val favTvShowsIdList = openUserDataFile(context)

    favTvShowsIdList.add(tvShowId)

    writeUserDataFile(context, Gson().toJson(favTvShowsIdList))
}

fun removeTvShowToFavourites(context: Context, tvShowId: Int) {
    val favTvShowsIdList = openUserDataFile(context)

    favTvShowsIdList.remove(tvShowId)

    writeUserDataFile(context, Gson().toJson(favTvShowsIdList))
}