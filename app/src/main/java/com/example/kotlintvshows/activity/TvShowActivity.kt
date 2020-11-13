package com.example.kotlintvshows.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlintvshows.R
import com.example.kotlintvshows.interfaces.TvShowContract
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.utils.*
import com.example.kotlintvshows.utils.Constants.Companion.LIST_ACTIVITY
import com.example.kotlintvshows.utils.Constants.Companion.MAIN_ACTIVITY
import com.example.kotlintvshows.utils.Constants.Companion.ORIGIN
import com.example.kotlintvshows.utils.Constants.Companion.REQUEST_TYPE
import com.example.kotlintvshows.utils.Constants.Companion.TV_SHOW
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tvshow.*

class TvShowActivity : AppCompatActivity(), TvShowContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow)

        val tvShow: TvShow = intent.getSerializableExtra(TV_SHOW) as TvShow
        initUI(tvShow)
    }

    override fun onBackPressed() {
        val origin = intent.getSerializableExtra(ORIGIN)
        val requestType = intent.getSerializableExtra(REQUEST_TYPE) as String?
        finish()
        when (origin) {
            MAIN_ACTIVITY -> launchMainActivity(this)
            LIST_ACTIVITY -> launchTvShowsListActivity(this, requestType)
            else -> launchMainActivity(this)
        }
    }

    private fun initUI(tvShow: TvShow) {
        var showAdded: Boolean

        Picasso
            .get()
            .load("https://image.tmdb.org/t/p/w500/${tvShow.poster_path}")
            .fit()
            .centerCrop()
            .into(imgPoster)
        tvName.text = tvShow.name
        tvOverview.text = tvShow.overview

        showAdded = if (openUserDataFile(this).contains(tvShow.id)){
            setSubscribeBtnClicked()
            true
        } else {
            setSubscribeBtnUnClicked()
            false
        }

        btnSubscribe.setOnClickListener {
            showAdded = if (!showAdded) {
                isSubscribeBtnEnabled(false)
                setSubscribeBtnClicked()
                addTvShowToFavourites(this, tvShow.id)
                isSubscribeBtnEnabled(true)
                showToast(resources.getString(R.string.show_added))
                true
            } else {
                isSubscribeBtnEnabled(false)
                setSubscribeBtnUnClicked()
                removeTvShowToFavourites(this, tvShow.id)
                isSubscribeBtnEnabled(true)
                showToast(resources.getString(R.string.show_removed))
                false
            }
        }
    }

    override fun setSubscribeBtnUnClicked() {
        btnSubscribe.setBackgroundResource(R.drawable.btn_subscribe)
        btnSubscribe.setTextColor(resources.getColor(R.color.colorTextLight))
        btnSubscribe.text = resources.getString(R.string.subscribe)
    }

    override fun setSubscribeBtnClicked() {
        btnSubscribe.setBackgroundResource(R.drawable.btn_subscribe_clicked)
        btnSubscribe.setTextColor(resources.getColor(R.color.colorTextDark))
        btnSubscribe.text = resources.getString(R.string.added)
    }

    override fun isSubscribeBtnEnabled(boolean: Boolean) {
        btnSubscribe.isEnabled = boolean
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}