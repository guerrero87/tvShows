package com.example.kotlintvshows.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        loadApiData()
    }

    private fun initRecyclerView() {
        val gridLayoutManager = GridLayoutManager(this, 3)


        recyclerTvShows.layoutManager = gridLayoutManager
    }

    fun loadApiData() {
        val locale = Locale.getDefault().language
        var page = 1


        val call: Call<TopTvShows> = TmdbManager.getTopTvShows(locale, page)

        call.enqueue(object: Callback<TopTvShows> {
            override fun onFailure(call: Call<TopTvShows>, t: Throwable) {
                Toast.makeText(applicationContext, "ERROR: ${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<TopTvShows>, response: Response<TopTvShows>) {
                handleResponse(response.body()!!)
            }
        })
    }

    fun handleResponse(topTvShows: TopTvShows) {
        val topTvShowsList: ArrayList<TvShow> = ArrayList(topTvShows.results)

        val adapter: TmdbAdapter? = TmdbAdapter(topTvShowsList, this)

        recyclerTvShows.adapter = adapter
    }

    override fun getName(tvShow: TvShow, position: Int) {
        val intent: Intent = Intent(this, TvShowActivity::class.java)
        intent.putExtra("TVSHOW", tvShow)
        startActivity(intent)
    }
}

