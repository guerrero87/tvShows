package com.example.kotlintvshows.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.kotlintvshows.activity.MainActivity
import com.example.kotlintvshows.activity.TvShowActivity
import com.example.kotlintvshows.activity.TvShowListActivity
import com.example.kotlintvshows.utils.Constants.Companion.LIST_ACTIVITY
import com.example.kotlintvshows.utils.Constants.Companion.MAIN_ACTIVITY
import com.example.kotlintvshows.utils.Constants.Companion.ORIGIN
import com.example.kotlintvshows.utils.Constants.Companion.REQUEST_TYPE
import com.example.kotlintvshows.utils.Constants.Companion.TV_SHOW_ID

fun launchTvShowActivityMain(context: Context, tvShowId: Int) {
    launchTvShowActivity(context, tvShowId, MAIN_ACTIVITY, null)
}

fun launchTvShowActivityList(context: Context, tvShowId: Int, requestType: String?) {
    launchTvShowActivity(context, tvShowId, LIST_ACTIVITY, requestType)
}

private fun launchTvShowActivity(context: Context, tvShowId: Int, origin: String, requestType: String?) {
    val intent = Intent(context, TvShowActivity::class.java)
    intent.putExtra(TV_SHOW_ID, tvShowId)
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


