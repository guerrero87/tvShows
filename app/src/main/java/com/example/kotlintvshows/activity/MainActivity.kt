package com.example.kotlintvshows.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintvshows.R
import com.example.kotlintvshows.tmdbAPI.manager.TmdbManager
import com.example.kotlintvshows.tmdbAPI.model.TopTvShows
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initRecyclerView()
        loadApiData()
    }

    private fun initRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerTvShows.layoutManager = layoutManager
    }

    fun loadApiData() {
        var call: Call<TopTvShows> = TmdbManager.getTopTvShows()

        call.enqueue(object: Callback<TopTvShows> {
            override fun onFailure(call: Call<TopTvShows>, t: Throwable) {
                Toast.makeText(applicationContext, "ERROR: ${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<TopTvShows>, response: Response<TopTvShows>) {
                val response = response.body()!!
            }
        })
    }

    fun handleResponse(topTvShows: TopTvShows) {
        var topTvShowsList: ArrayList<TvShow> = ArrayList(topTvShows.results)

//        var adapter: MyAdapter? = MyAdapter(topTvShowsList, this)
//
//        recyclerTvShows.adapter = adapter
    }
}

