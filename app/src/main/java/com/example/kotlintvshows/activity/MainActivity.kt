package com.example.kotlintvshows.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintvshows.R
import com.example.kotlintvshows.adapter.TmdbAdapter
import com.example.kotlintvshows.base.BaseActivity
import com.example.kotlintvshows.interfaces.Contract
import com.example.kotlintvshows.presenter.Presenter
import com.example.kotlintvshows.tmdbAPI.manager.TmdbManager
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.tmdbAPI.model.TvShowsList
import com.example.kotlintvshows.utils.createOrOpenUserDataFile
import com.example.kotlintvshows.utils.launchTvShowActivity
import com.example.kotlintvshows.utils.launchTvShowsListActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity<Presenter>(), Contract.View {

    private val deviceLocale: String = Locale.getDefault().language
    private var favTvShowsList: ArrayList<TvShow> = ArrayList()
    private lateinit var favTvShowsAdapter: TmdbAdapter

    override fun createPresenter(context: Context): Presenter {
        return Presenter(this, TmdbManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchSingleTvShowDetails()
        initListeners()
    }

    private fun initListeners() {
        tvTopTvSHows.setOnClickListener {
            finish()
            launchTvShowsListActivity(this, tvTopTvSHows.text as String)
        }
    }

    private fun initFavShowsRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL,
            false)
        favTvShowsAdapter = TmdbAdapter(favTvShowsList, presenter)

        recyclerFavShows.layoutManager = linearLayoutManager
        recyclerFavShows.adapter = favTvShowsAdapter
        favTvShowsAdapter.notifyDataSetChanged()
    }

    override fun fetchTvShowListDetails() {
        TODO("DOES NOT APPLY HERE")
    }

    override fun fetchSingleTvShowDetails() {
        val favTvShowsIdList: MutableList<Int> = createOrOpenUserDataFile(this)
        for (tvShowId in favTvShowsIdList) {
            presenter.fetchSingleTvShowData(tvShowId, deviceLocale)
        }
    }

    override fun showSingleTvShowResponseDetails(tvShow: TvShow) {
        favTvShowsList.add(tvShow)
        initFavShowsRecyclerView()
    }

    override fun showTvSHowListResponseDetails(tvshows: TvShowsList) {
        TODO("DOES NOT APPLY HERE")
    }

    override fun loadNextResultsPage(tvshows: TvShowsList) {
        TODO("DOES NOT APPLY HERE")
    }

    override fun onTvShowPressed(tvShow: TvShow) {
        finish()
        launchTvShowActivity(applicationContext, tvShow)
    }

    override fun onTvShowLongPressed() {
        Toast.makeText(this, "LONG PRESSED", Toast.LENGTH_SHORT).show()
    }

    override fun showError() {
        Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
    }
}