package com.example.kotlintvshows.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintvshows.R
import com.example.kotlintvshows.adapter.TmdbAdapter
import com.example.kotlintvshows.interfaces.OnTvShowClicked
import com.example.kotlintvshows.tmdbAPI.manager.TmdbManager
import com.example.kotlintvshows.tmdbAPI.model.TopTvShows
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), OnTvShowClicked {

    private var page = 1

    private val topTvShowsList: ArrayList<TvShow> = ArrayList()
    lateinit var adapter: TmdbAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        loadApiData(Locale.getDefault().language, page)
    }

    private fun initRecyclerView() {
        val gridLayoutManager = GridLayoutManager(this, 3)
        recyclerTvShows.layoutManager = gridLayoutManager

        recyclerTvShows.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > 0) {
                    val visibleItemCount: Int = gridLayoutManager.childCount
                    val pastVisibleItem: Int = gridLayoutManager.findFirstCompletelyVisibleItemPosition()
                    val total: Int = adapter.itemCount

                    if ((visibleItemCount + pastVisibleItem) > total) {
                        page++
                        loadApiData(Locale.getDefault().language, page)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun loadApiData(deviceLocale: String, page: Int) {

        val call: Call<TopTvShows> = TmdbManager.getTopTvShows(deviceLocale, page)

        call.enqueue(object: Callback<TopTvShows> {
            override fun onFailure(call: Call<TopTvShows>, t: Throwable) {
                Toast.makeText(applicationContext, "ERROR: ${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<TopTvShows>, response: Response<TopTvShows>) {
                handleResponse(response.body()!!)
            }
        })
    }

    private fun handleResponse(topTvShows: TopTvShows) {
        topTvShowsList.addAll(topTvShows.results)

        adapter = TmdbAdapter(topTvShowsList, this)

        recyclerTvShows.adapter = adapter
    }

    override fun getName(tvShow: TvShow, position: Int) {
        val intent = Intent(this, TvShowActivity::class.java)
        intent.putExtra("TVSHOW", tvShow)
        startActivity(intent)
    }
}



