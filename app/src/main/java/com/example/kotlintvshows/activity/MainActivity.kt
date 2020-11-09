package com.example.kotlintvshows.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintvshows.R
import com.example.kotlintvshows.adapter.TmdbAdapter
import com.example.kotlintvshows.interfaces.MainActivityBehaviour
import com.example.kotlintvshows.tmdbAPI.manager.TmdbManager
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.utils.Constants
import com.example.kotlintvshows.utils.readUserDataFile
import com.example.kotlintvshows.utils.writeUserDataFile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), MainActivityBehaviour {

    private var favTvShowsIdList: MutableList<Int> = ArrayList()

    private var favTvShowsList: ArrayList<TvShow> = ArrayList()
    private lateinit var favTvShowsAdapter: TmdbAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createUserDataFile()
        initFavShowsRecyclerView()
        tvTopTvSHows.setOnClickListener {
            val intent = Intent(this, TvShowListActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun createUserDataFile() {
        val filePath = File(this.filesDir.toString() + "/" + Constants.FILE_NAME)

        if (!filePath.exists()) {
            //FILE DOES NOT EXIST, CREATE IT
            writeUserDataFile(this, Gson().toJson(favTvShowsIdList))
        } else {
            //FILE EXISTS, READ CONTENT
            favTvShowsIdList = Gson().fromJson(readUserDataFile(this),
                object : TypeToken<MutableList<Int>>(){}.type)
        }
    }

    private fun initFavShowsRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL,
            false)
        favTvShowsAdapter = TmdbAdapter(favTvShowsList, this)

        recyclerFavShows.layoutManager = linearLayoutManager
        recyclerFavShows.adapter = favTvShowsAdapter

        for (tvShowId in favTvShowsIdList) {
            loadFavTvShows(Locale.getDefault().language, tvShowId, favTvShowsAdapter)
        }
    }

    private fun loadFavTvShows(deviceLocale: String, tvShowId: Int, favTvShowsAdapter: TmdbAdapter) {
        val call: Call<TvShow> = TmdbManager.getTvShow(tvShowId, deviceLocale)

        call.enqueue(object: Callback<TvShow> {
            override fun onFailure(call: Call<TvShow>, t: Throwable) {
                Toast.makeText(applicationContext, "ERROR: ${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<TvShow>, response: Response<TvShow>) {
                handleTvShowResponse(response.body()!!, favTvShowsAdapter)
            }
        })
    }

    private fun handleTvShowResponse(tvShow: TvShow, favTvShowsAdapter: TmdbAdapter) {
        favTvShowsList.add(tvShow)
        favTvShowsAdapter.notifyDataSetChanged()
    }

    override fun onTvShowPressed(tvShow: TvShow) {
        val intent = Intent(this, TvShowActivity::class.java)
        intent.putExtra("TVSHOW", tvShow)
        finish()
        startActivity(intent)
    }

    override fun onTvShowLongPressed() {
        Toast.makeText(this, "LONG PRESSED", Toast.LENGTH_SHORT).show()
    }
}