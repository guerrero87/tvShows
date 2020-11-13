package com.example.kotlintvshows.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.kotlintvshows.activity.MainActivity
import com.example.kotlintvshows.activity.TvShowActivity
import com.example.kotlintvshows.activity.TvShowListActivity
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.utils.Constants.Companion.LIST_ACTIVITY
import com.example.kotlintvshows.utils.Constants.Companion.MAIN_ACTIVITY
import com.example.kotlintvshows.utils.Constants.Companion.ORIGIN
import com.example.kotlintvshows.utils.Constants.Companion.REQUEST_TYPE
import com.example.kotlintvshows.utils.Constants.Companion.TV_SHOW

fun launchTvShowActivityMain(context: Context, tvShow: TvShow) {
    launchTvShowActivity(context, tvShow, MAIN_ACTIVITY, null)
}

fun launchTvShowActivityList(context: Context, tvShow: TvShow, requestType: String?) {
    launchTvShowActivity(context, tvShow, LIST_ACTIVITY, requestType)
}

private fun launchTvShowActivity(context: Context, tvShow: TvShow, origin: String, requestType: String?) {
    val intent = Intent(context, TvShowActivity::class.java)
    intent.putExtra(TV_SHOW, tvShow)
    intent.putExtra(ORIGIN, origin)
    intent.putExtra(REQUEST_TYPE, requestType)
    startActivity(context, intent, null)
}

fun launchTvShowsListActivity(context: Context, requestType: String?) {
    val intent = Intent(context, TvShowListActivity::class.java)
    intent.putExtra(REQUEST_TYPE, requestType)
    startActivity(context, intent, null)
}

fun launchMainActivity(context: Context) {
    val intent = Intent(context, MainActivity::class.java)
    startActivity(context, intent, null)
}


