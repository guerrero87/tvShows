package com.example.kotlintvshows.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlintvshows.R
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.utils.Constants
import com.example.kotlintvshows.utils.readUserDataFile
import com.example.kotlintvshows.utils.writeUserDataFile
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

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }

    private fun getUserDataFile() {

        val file = File(this.filesDir.toString() + "/" + Constants.fileName)

        if (file.exists()) {
            //file exists, show content in toast
            favTvShowsIdList = Gson().fromJson(readUserDataFile(this),
                object : TypeToken<MutableList<Int>>(){}.type)

        } else {
            favTvShowsIdList = ArrayList()
            Toast.makeText(this, "ERROR: FILE NOT FOUND", Toast.LENGTH_SHORT).show()
            btnSubscribe.isEnabled = false
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

        if (favTvShowsIdList.contains(tvShow.id)){
            setSubscribeBtnClicked()
            showAdded = true
        } else {
            setSubscribeBtnUnClicked()
            showAdded = false
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
        btnSubscribe.text = Constants.suscribe
    }

    private fun setSubscribeBtnClicked() {
        btnSubscribe.setBackgroundResource(R.drawable.btn_subscribe_clicked)
        btnSubscribe.setTextColor(resources.getColor(R.color.colorTextDark))
        btnSubscribe.text = Constants.added
    }

    private fun addTvShowToFavourites(tvShowId: Int) {

        val filePath = File(this.filesDir.toString() + "/" + Constants.fileName)

        if (filePath.exists()) {
            //OPEN THE FILE AND EXTRACT CONTENT TO STRING
            favTvShowsIdList = Gson().fromJson(readUserDataFile(this),
                object : TypeToken<MutableList<Int>>(){}.type)

            //ADD SELECTED TVSHOW ID TO LIST
            favTvShowsIdList.add(tvShowId)

            //WRITE THE UPDATED LIST TO THE FILE
            writeUserDataFile(this, Gson().toJson(favTvShowsIdList))
        }
        btnSubscribe.isEnabled = true
        Toast.makeText(this, "SHOW ADDED TO FAVOURITES", Toast.LENGTH_SHORT).show()
    }

    private fun removeTvShowToFavourites(tvShowId: Int) {

        val filePath = File(this.filesDir.toString() + "/" + Constants.fileName)

        if (filePath.exists()) {
            //OPEN THE FILE AND EXTRACT CONTENT TO STRING
            favTvShowsIdList = Gson().fromJson(readUserDataFile(this),
                object : TypeToken<MutableList<Int>>(){}.type)

            //REMOVE SELECTED TVSHOW ID TO LIST
            favTvShowsIdList.remove(tvShowId)

            //WRITE THE UPDATED LIST TO THE FILE
            writeUserDataFile(this, Gson().toJson(favTvShowsIdList))
        }
        btnSubscribe.isEnabled = true
        Toast.makeText(this, "SHOW REMOVED FROM FAVOURITES", Toast.LENGTH_SHORT).show()
    }
}