package com.example.kotlintvshows.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlintvshows.R
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tvshow.*

class TvShowActivity : AppCompatActivity() {

    var btnClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow)

        val tvShow: TvShow = intent.getSerializableExtra("TVSHOW") as TvShow

        initUI(tvShow)
    }

    private fun initUI(tvShow: TvShow) {
        Picasso
            .get()
            .load("https://image.tmdb.org/t/p/w500/${tvShow.poster_path}")
            .fit()
            .centerCrop()
            .into(imgPoster)
        tvName.text = tvShow.name
        tvOverview.text = tvShow.overview

        btnSubscribe.setOnClickListener(View.OnClickListener {
            if (!btnClicked) {
                btnSubscribe.setBackgroundResource(R.drawable.btn_subscribe_clicked)
                btnSubscribe.setTextColor(resources.getColor(R.color.colorTextDark))
                btnSubscribe.text = "ADDED"
                btnClicked = true
            } else {
                btnSubscribe.setBackgroundResource(R.drawable.btn_subscribe)
                btnSubscribe.setTextColor(resources.getColor(R.color.colorTextLight))
                btnSubscribe.text = "SUBSCRIBE"
                btnClicked = false
            }
        })
    }
}