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
    private var requestType: String = ""
    private var TvShowsList: MutableList<TvShow> = ArrayList()
    private lateinit var topTvShowsAdapter: TmdbAdapter

    override fun createPresenter(context: Context): TvShowsListPresenter {
        return TvShowsListPresenter(this, TmdbManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshows_list)
        requestType = intent.getStringExtra(Constants.REQUEST_TYPE)
        fetchTvShowDetails()
    }

    override fun fetchTvShowDetails() {
        presenter.fetchTvShowsData(deviceLocale, page, requestType)
    }

    override fun showResponseDetails(tvshows: TopTvShows) {
        tvTvShows.text = requestType
        TvShowsList.addAll(tvshows.results)
        initTopTvShowsRecyclerView(TvShowsList as ArrayList<TvShow>)
    }

    override fun loadNextResultsPage(tvshows: TopTvShows) {
        TvShowsList.addAll(tvshows.results)
        topTvShowsAdapter.notifyDataSetChanged()
    }

    override fun showError() {
        TODO("Not yet implemented")
    }

    private fun initTopTvShowsRecyclerView(topTvShowsList: ArrayList<TvShow>) {
        val gridLayoutManager = GridLayoutManager(
            this,
            Constants.GRID_LAYOUT_COLUMN_NUMBER)

        topTvShowsAdapter = TmdbAdapter(topTvShowsList, object: MainActivityBehaviour {
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
                    presenter.fetchNextPageTvShowsData(deviceLocale, page, requestType)
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }
}