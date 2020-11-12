package com.example.kotlintvshows.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlintvshows.R
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.utils.Constants.Companion.LIST_ACTIVITY
import com.example.kotlintvshows.utils.Constants.Companion.MAIN_ACTIVITY
import com.example.kotlintvshows.utils.Constants.Companion.REQUEST_TYPE
import com.example.kotlintvshows.utils.launchMainActivity
import com.example.kotlintvshows.utils.launchTvShowsListActivity
import com.example.kotlintvshows.utils.openUserDataFile
import com.example.kotlintvshows.utils.writeUserDataFile
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tvshow.*

class TvShowActivity : AppCompatActivity() {

    private lateinit var favTvShowsIdList: MutableList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow)

        val tvShow: TvShow = intent.getSerializableExtra("TVSHOW") as TvShow

        favTvShowsIdList = openUserDataFile(this)

        if (favTvShowsIdList.isEmpty()) {
            btnSubscribe.isEnabled = false
        }

        initUI(tvShow)
    }

    override fun onBackPressed() {
        val origin = intent.getSerializableExtra("ORIGIN")
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

        showAdded = if (favTvShowsIdList.contains(tvShow.id)){
            setSubscribeBtnClicked()
            true
        } else {
            setSubscribeBtnUnClicked()
            false
        }

        btnSubscribe.setOnClickListener {

            if (!showAdded) {
                btnSubscribe.isEnabled = false
                setSubscribeBtnClicked()
                addTvShowToFavourites(tvShow.id)
                showAdded = true
            } else {
                btnSubscribe.isEnabled = false
                setSubscribeBtnUnClicked()
                removeTvShowToFavourites(tvShow.id)
                showAdded = false
            }
        }
    }

    private fun setSubscribeBtnUnClicked() {
        btnSubscribe.setBackgroundResource(R.drawable.btn_subscribe)
        btnSubscribe.setTextColor(resources.getColor(R.color.colorTextLight))
        btnSubscribe.text = resources.getString(R.string.subscribe)
    }

    private fun setSubscribeBtnClicked() {
        btnSubscribe.setBackgroundResource(R.drawable.btn_subscribe_clicked)
        btnSubscribe.setTextColor(resources.getColor(R.color.colorTextDark))
        btnSubscribe.text = resources.getString(R.string.added)
    }

    private fun addTvShowToFavourites(tvShowId: Int) {
        //ADD SELECTED TVSHOW ID TO LIST
        favTvShowsIdList.add(tvShowId)

        //WRITE THE UPDATED LIST TO THE FILE
        writeUserDataFile(this, Gson().toJson(favTvShowsIdList))

        btnSubscribe.isEnabled = true
        Toast.makeText(this, "SHOW ADDED TO FAVOURITES", Toast.LENGTH_SHORT).show()
    }

    private fun removeTvShowToFavourites(tvShowId: Int) {
        //REMOVE SELECTED TVSHOW ID TO LIST
        favTvShowsIdList.remove(tvShowId)

        //WRITE THE UPDATED LIST TO THE FILE
        writeUserDataFile(this, Gson().toJson(favTvShowsIdList))

        btnSubscribe.isEnabled = true
        Toast.makeText(this, "SHOW REMOVED FROM FAVOURITES", Toast.LENGTH_SHORT).show()
    }
}