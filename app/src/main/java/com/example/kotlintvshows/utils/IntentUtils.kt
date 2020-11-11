package com.example.kotlintvshows.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.kotlintvshows.activity.TvShowActivity
import com.example.kotlintvshows.activity.TvShowListActivity
import com.example.kotlintvshows.tmdbAPI.model.TvShow

fun launchTvShowsListActivity(context: Context, requestType: String) {
    val intent = Intent(context, TvShowListActivity::class.java)
    intent.putExtra(Constants.REQUEST_TYPE, requestType)
    startActivity(context, intent, null)
}

fun launchTvShowActivity(context: Context, tvShow: TvShow) {
    val intent = Intent(context, TvShowActivity::class.java)
    intent.putExtra("TVSHOW", tvShow)
    startActivity(context, intent, null)
}
