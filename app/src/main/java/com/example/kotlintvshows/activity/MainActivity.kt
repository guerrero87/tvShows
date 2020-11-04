package com.example.kotlintvshows.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintvshows.R
import com.example.kotlintvshows.adapter.TmdbAdapter
import com.example.kotlintvshows.interfaces.MainActivityBehaviour
import com.example.kotlintvshows.tmdbAPI.manager.TmdbManager
import com.example.kotlintvshows.tmdbAPI.model.TopTvShows
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

class MainActivity() : AppCompatActivity(), MainActivityBehaviour {

    private var page = 1

    private var favTvShowsIdList: MutableList<Int> = ArrayList()
    private var topTvShowsList: ArrayList<TvShow> = ArrayList()
    private var favTvShowsList: ArrayList<TvShow> = ArrayList()

    private lateinit var favTvShowsAdapter: TmdbAdapter

    private val deviceLocale: String = Locale.getDefault().language

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createUserDataFile()
        initFavShowsRecyclerView()
        initTopTvShowsRecyclerView()
    }

    private fun createUserDataFile() {
        val filePath = File(this.filesDir.toString() + "/" + Constants.fileName)

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

    private fun initTopTvShowsRecyclerView() {
        val gridLayoutManager = GridLayoutManager(
            this,
            Constants.gridLayoutColumnNumber)
        val topTvShowsAdapter = TmdbAdapter(topTvShowsList, this)

        recyclerTvShows.layoutManager = gridLayoutManager
        recyclerTvShows.adapter = topTvShowsAdapter

        loadTopTvShows(deviceLocale, page, topTvShowsAdapter)

        recyclerTvShows.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                detectRecyclerScrollPosition(dy, gridLayoutManager, topTvShowsAdapter)

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun detectRecyclerScrollPosition(dy: Int, gridLayoutManager: GridLayoutManager, adapter: TmdbAdapter) {
        if (dy > 0) {
            val visibleItemCount: Int = gridLayoutManager.childCount
            val pastVisibleItem: Int = gridLayoutManager.findFirstCompletelyVisibleItemPosition()
            val total: Int = adapter.itemCount

            if ((visibleItemCount + pastVisibleItem) > total) {
                page++
                loadTopTvShows(deviceLocale, page, adapter)
            }
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

    private fun loadTopTvShows(deviceLocale: String, page: Int, topTvShowsAdapter: TmdbAdapter) {

        val call: Call<TopTvShows> = TmdbManager.getTopTvShows(deviceLocale, page)

        call.enqueue(object: Callback<TopTvShows> {
            override fun onFailure(call: Call<TopTvShows>, t: Throwable) {
                Toast.makeText(applicationContext, "ERROR: ${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<TopTvShows>, response: Response<TopTvShows>) {
                handleTopTvShowsResponse(response.body()!!, topTvShowsAdapter)
            }
        })
    }

    private fun handleTopTvShowsResponse(topTvShows: TopTvShows, topTvShowsAdapter: TmdbAdapter) {
        topTvShowsList.addAll(topTvShows.results)
        topTvShowsAdapter.notifyDataSetChanged()
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