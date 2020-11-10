package com.example.kotlintvshows.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.kotlintvshows.activity.TvShowListActivity

fun launchTvShowsListActivity(context: Context, requestType: String) {
    val intent = Intent(context, TvShowListActivity::class.java)
    intent.putExtra(Constants.REQUEST_TYPE, requestType)
    startActivity(context, intent, null)
}