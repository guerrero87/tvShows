package com.example.kotlintvshows.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlintvshows.R
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tvshow.*

class TvShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow)

        val tvShow: TvShow = intent.getSerializableExtra("TVSHOW") as TvShow

        Picasso
            .get()
            .load("https://image.tmdb.org/t/p/w500/${tvShow.poster_path}")
            .resize(500, 750)
            .into(imgPoster)
        tvName.text = tvShow.name
        tvOverview.text = tvShow.overview

    }
}