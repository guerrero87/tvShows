package com.example.kotlintvshows.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintvshows.R
import com.example.kotlintvshows.adapter.TmdbAdapter
import com.example.kotlintvshows.base.BaseActivity
import com.example.kotlintvshows.interfaces.Contract
import com.example.kotlintvshows.presenter.Presenter
import com.example.kotlintvshows.tmdbAPI.manager.TmdbManager
import com.example.kotlintvshows.tmdbAPI.model.TvShowsList
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.utils.Constants
import com.example.kotlintvshows.utils.detectRecyclerScrollPosition
import com.example.kotlintvshows.utils.launchTvShowActivity
import kotlinx.android.synthetic.main.activity_tvshows_list.*

class TvShowListActivity: BaseActivity<Presenter>(), Contract.View {

    private val deviceLocale: String = java.util.Locale.getDefault().language
    private var page = 1
    private var requestType: String = ""
    private var tvShowsList: MutableList<TvShow> = ArrayList()
    private lateinit var topTvShowsAdapter: TmdbAdapter

    override fun createPresenter(context: Context): Presenter {
        return Presenter(this, TmdbManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshows_list)
        requestType = intent.getStringExtra(Constants.REQUEST_TYPE)
        fetchTvShowListDetails()
    }

    override fun fetchTvShowListDetails() {
        presenter.fetchTvShowsData(deviceLocale, page, requestType)
    }

    override fun fetchSingleTvShowDetails() {
        TODO("DOES NOT APPLY HERE")
    }

    override fun showSingleTvShowResponseDetails(tvShow: TvShow) {
        TODO("DOES NOT APPLY HERE")
    }

    override fun showTvSHowListResponseDetails(tvshows: TvShowsList) {
        tvTvShows.text = requestType
        tvShowsList.addAll(tvshows.results)
        initTopTvShowsRecyclerView(tvShowsList as ArrayList<TvShow>)
    }

    override fun loadNextResultsPage(tvshows: TvShowsList) {
        tvShowsList.addAll(tvshows.results)
        topTvShowsAdapter.notifyDataSetChanged()
    }

    override fun onTvShowPressed(tvShow: TvShow) {
        finish()
        launchTvShowActivity(applicationContext, tvShow)
    }

    override fun onTvShowLongPressed() {
        Toast.makeText(applicationContext, "LONG PRESSED", Toast.LENGTH_SHORT).show()
    }

    override fun showError() {
        Toast.makeText(applicationContext, "ERROR", Toast.LENGTH_SHORT).show()
    }

    private fun initTopTvShowsRecyclerView(topTvShowsList: ArrayList<TvShow>) {
        val gridLayoutManager = GridLayoutManager(
            this,
            Constants.GRID_LAYOUT_COLUMN_NUMBER)

        topTvShowsAdapter = TmdbAdapter(topTvShowsList, presenter)

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