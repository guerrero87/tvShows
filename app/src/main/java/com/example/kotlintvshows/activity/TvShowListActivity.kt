package com.example.kotlintvshows.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintvshows.R
import com.example.kotlintvshows.adapter.TvShowListAdapter
import com.example.kotlintvshows.base.BaseActivity
import com.example.kotlintvshows.interfaces.TvShowListContract
import com.example.kotlintvshows.presenter.TvShowListPresenter
import com.example.kotlintvshows.tmdbAPI.manager.TmdbManager
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.tmdbAPI.model.TvShowsList
import com.example.kotlintvshows.utils.Constants
import com.example.kotlintvshows.utils.Constants.Companion.GRID_LAYOUT_COLUMN_NUMBER
import com.example.kotlintvshows.utils.Constants.Companion.REQUEST_TYPE
import com.example.kotlintvshows.utils.detectRecyclerScrollPosition
import com.example.kotlintvshows.utils.launchMainActivity
import com.example.kotlintvshows.utils.launchTvShowActivityList
import kotlinx.android.synthetic.main.activity_tvshows_list.*

class TvShowListActivity: BaseActivity<TvShowListPresenter>(), TvShowListContract.View {

    private val deviceLocale: String = java.util.Locale.getDefault().language
    private var page = 1
    private var requestType: String = ""
    private var tvShowsList: MutableList<TvShow> = ArrayList()
    private lateinit var tvShowListAdapter: TvShowListAdapter

    override fun createPresenter(context: Context): TvShowListPresenter {
        return TvShowListPresenter(this, TmdbManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshows_list)
        requestType = intent.getStringExtra(REQUEST_TYPE)
        fetchTvShowListDetails()
    }

    override fun onBackPressed() {
        finish()
        launchMainActivity(this)
    }

    private fun initTvShowListRecyclerView(tvShowList: ArrayList<TvShow>) {
        val gridLayoutManager = GridLayoutManager(
            this,
            GRID_LAYOUT_COLUMN_NUMBER)

        tvShowListAdapter = TvShowListAdapter(this, tvShowList, this)

        recyclerTvShows.layoutManager = gridLayoutManager
        recyclerTvShows.adapter = tvShowListAdapter

        recyclerTvShows.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (detectRecyclerScrollPosition(dy, gridLayoutManager, tvShowListAdapter)) {
                    page++
                    presenter.fetchNextPageTvShowsData(applicationContext, deviceLocale, page, requestType)
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun fetchTvShowListDetails() {
        presenter.fetchTvShowsData(this, deviceLocale, page, requestType)
    }

    override fun showTvSHowListResponseDetails(tvshows: TvShowsList) {
        tvTvShows.text = requestType
        tvShowsList.addAll(tvshows.results)
        initTvShowListRecyclerView(tvShowsList as ArrayList<TvShow>)
    }

    override fun loadNextResultsPage(tvshows: TvShowsList) {
        tvShowsList.addAll(tvshows.results)
        tvShowListAdapter.notifyDataSetChanged()
    }

    override fun onTvShowPressed(tvShowId: Int) {
        finish()
        launchTvShowActivityList(applicationContext, tvShowId, requestType)
    }

    override fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}