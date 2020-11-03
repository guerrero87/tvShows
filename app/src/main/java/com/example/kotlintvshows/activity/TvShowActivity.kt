package com.example.kotlintvshows.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlintvshows.R
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tvshow.*
import java.io.File

class TvShowActivity : AppCompatActivity() {

    private lateinit var favTvShowsIdList: MutableList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow)

        val tvShow: TvShow = intent.getSerializableExtra("TVSHOW") as TvShow
        getUserDataFile()
        initUI(tvShow)
    }

    private fun getUserDataFile() {
        val fileName = "favTvShows.json"

        val file = File(this.filesDir.toString() + "/" + fileName)

        if (file.exists()) {
            //file exists, show content in toast
            this.openFileInput(fileName).use { stream ->
                val jsonStringToRead = stream.bufferedReader().use {
                    it.readText()
                }
                favTvShowsIdList = Gson().fromJson(jsonStringToRead,
                    object : TypeToken<MutableList<Int>>(){}.type)
            }
        } else {
            favTvShowsIdList = ArrayList()
            Toast.makeText(this, "ERROR: FILE NOT FOUND", Toast.LENGTH_SHORT).show()
            btnSubscribe.isEnabled = false
            }
        }


    private fun initUI(tvShow: TvShow) {
        var showAdded = false

        Picasso
            .get()
            .load("https://image.tmdb.org/t/p/w500/${tvShow.poster_path}")
            .fit()
            .centerCrop()
            .into(imgPoster)
        tvName.text = tvShow.name
        tvOverview.text = tvShow.overview

        if (favTvShowsIdList.contains(tvShow.id)){
            btnSubscribe.setBackgroundResource(R.drawable.btn_subscribe_clicked)
            btnSubscribe.setTextColor(resources.getColor(R.color.colorTextDark))
            btnSubscribe.text = "ADDED"
            showAdded = true
        } else {
            btnSubscribe.setBackgroundResource(R.drawable.btn_subscribe)
            btnSubscribe.setTextColor(resources.getColor(R.color.colorTextLight))
            btnSubscribe.text = "SUBSCRIBE"
            showAdded = false
        }

        btnSubscribe.setOnClickListener {

            if (!showAdded) {
                btnSubscribe.isEnabled = false
                btnSubscribe.setBackgroundResource(R.drawable.btn_subscribe_clicked)
                btnSubscribe.setTextColor(resources.getColor(R.color.colorTextDark))
                addTvShowToFavourites(tvShow.id)
                btnSubscribe.text = "ADDED"
                showAdded = true
            } else {
                btnSubscribe.isEnabled = false
                btnSubscribe.setBackgroundResource(R.drawable.btn_subscribe)
                btnSubscribe.setTextColor(resources.getColor(R.color.colorTextLight))
                removeTvShowToFavourites(tvShow.id)
                btnSubscribe.text = "SUBSCRIBE"
                showAdded = false
            }
        }
    }

    private fun addTvShowToFavourites(tvShowId: Int) {

        val fileName = "favTvShows.json"

        val file = File(this.filesDir.toString() + "/" + fileName)

        if (file.exists()) {
            //OPEN THE FILE AND EXTRACT CONTENT TO STRING
            this.openFileInput(fileName).use { stream ->
                val jsonStringToRead = stream.bufferedReader().use {
                    it.readText()
                }

                //CONVERT STRING TO OBJECT
                val favTvShowsIdList = Gson().fromJson<MutableList<Int>>(jsonStringToRead,
                    object : TypeToken<MutableList<Int>>(){}.type)

                //ADD SELECTED TVSHOW ID TO LIST
                favTvShowsIdList.add(tvShowId)

                //CONVERT LIST BACK TO JSON STRING
                val jsonStringToWrite = Gson().toJson(favTvShowsIdList)

                //WRITE THE UPDATED LIST TO THE FILE
                this.openFileOutput(fileName, Context.MODE_PRIVATE).use { output ->
                    output.write(jsonStringToWrite.toByteArray())
                }
            }
        }
        btnSubscribe.isEnabled = true
        Toast.makeText(this, "SHOW ADDED TO FAVOURITES", Toast.LENGTH_SHORT).show()
    }

    private fun removeTvShowToFavourites(tvShowId: Int) {

        val fileName = "favTvShows.json"

        val file = File(this.filesDir.toString() + "/" + fileName)

        if (file.exists()) {
            //OPEN THE FILE AND EXTRACT CONTENT TO STRING
            this.openFileInput(fileName).use { stream ->
                val jsonStringToRead = stream.bufferedReader().use {
                    it.readText()
                }

                //CONVERT STRING TO OBJECT
                val favTvShowsIdList = Gson().fromJson<MutableList<Int>>(jsonStringToRead,
                    object : TypeToken<MutableList<Int>>(){}.type)

                //ADD SELECTED TVSHOW ID TO LIST
                favTvShowsIdList.remove(tvShowId)

                //CONVERT LIST BACK TO JSON STRING
                val jsonStringToWrite = Gson().toJson(favTvShowsIdList)

                //WRITE THE UPDATED LIST TO THE FILE
                this.openFileOutput(fileName, Context.MODE_PRIVATE).use { output ->
                    output.write(jsonStringToWrite.toByteArray())
                }
            }
        }
        btnSubscribe.isEnabled = true
        Toast.makeText(this, "SHOW REMOVED FROM FAVOURITES", Toast.LENGTH_SHORT).show()
    }
}