package com.example.kotlintvshows.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintvshows.R
import com.example.kotlintvshows.adapter.TmdbAdapter
import com.example.kotlintvshows.base.BaseActivity
import com.example.kotlintvshows.interfaces.MainActivityBehaviour
import com.example.kotlintvshows.interfaces.TvShowsContract
import com.example.kotlintvshows.presenter.TvShowsListPresenter
import com.example.kotlintvshows.tmdbAPI.manager.TmdbManager
import com.example.kotlintvshows.tmdbAPI.model.TopTvShows
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.utils.Constants
import com.example.kotlintvshows.utils.Constants.Companion.TOP_TV_SHOWS
import com.example.kotlintvshows.utils.detectRecyclerScrollPosition
import kotlinx.android.synthetic.main.activity_tvshows_list.*

class TvShowListActivity: BaseActivity<TvShowsListPresenter>(), TvShowsContract.View {

    private val deviceLocale: String = java.util.Locale.getDefault().language
    private var page = 1
    private lateinit var requestType: String

    override fun createPresenter(context: Context): TvShowsListPresenter {
        return TvShowsListPresenter(this, TmdbManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshows_list)
        fetchTvShowDetails()
    }

    override fun fetchTvShowDetails() {
        presenter.fetchTvShowsData(deviceLocale, page, TOP_TV_SHOWS)//requestType from get value from bundle
    }

    override fun showResponseDetails(tvshows: TopTvShows) {
        tvTopTvShows.text = TOP_TV_SHOWS

        initTopTvShowsRecyclerView(tvshows.results)
    }

    override fun showError() {
        TODO("Not yet implemented")
    }

    private fun initTopTvShowsRecyclerView(topTvShowsList: ArrayList<TvShow>) {
        val gridLayoutManager = GridLayoutManager(
            this,
            Constants.GRID_LAYOUT_COLUMN_NUMBER)

        val topTvShowsAdapter = TmdbAdapter(topTvShowsList, object: MainActivityBehaviour {
            override fun onTvShowPressed(tvShow: TvShow) {
                val intent = Intent(applicationContext, TvShowActivity::class.java)
                intent.putExtra("TVSHOW", tvShow)
                finish()
                startActivity(intent)
            }

            override fun onTvShowLongPressed() {
                Toast.makeText(applicationContext, "LONG PRESSED", Toast.LENGTH_SHORT).show()
            }
        })

        recyclerTvShows.layoutManager = gridLayoutManager
        recyclerTvShows.adapter = topTvShowsAdapter

        recyclerTvShows.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (detectRecyclerScrollPosition(dy, gridLayoutManager, topTvShowsAdapter)) {
                    page++
                    presenter.fetchTvShowsData(deviceLocale, page, TOP_TV_SHOWS)//requestType from get value from bundle
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }
}