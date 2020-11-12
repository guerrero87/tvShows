package com.example.kotlintvshows.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintvshows.R
import com.example.kotlintvshows.adapter.MainAdapter
import com.example.kotlintvshows.base.BaseActivity
import com.example.kotlintvshows.interfaces.MainContract
import com.example.kotlintvshows.presenter.MainPresenter
import com.example.kotlintvshows.tmdbAPI.manager.TmdbManager
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.utils.createOrOpenUserDataFile
import com.example.kotlintvshows.utils.launchTvShowActivityMain
import com.example.kotlintvshows.utils.launchTvShowsListActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity<MainPresenter>(), MainContract.View {

    private val deviceLocale: String = Locale.getDefault().language
    private var favTvShowsList: ArrayList<TvShow> = ArrayList()
    private lateinit var favTvShowsAdapter: MainAdapter

    override fun createPresenter(context: Context): MainPresenter {
        return MainPresenter(this, TmdbManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchSingleTvShowDetails()
        initListeners()
    }

    private fun initListeners() {
        tvPopularTvSHows.setOnClickListener {
            finish()
            launchTvShowsListActivity(this, tvPopularTvSHows.text as String)
        }
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
        favTvShowsAdapter = MainAdapter(favTvShowsList, presenter)

        recyclerFavShows.layoutManager = linearLayoutManager
        recyclerFavShows.adapter = favTvShowsAdapter
        favTvShowsAdapter.notifyDataSetChanged()
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

    override fun onTvShowPressed(tvShow: TvShow) {
        finish()
        launchTvShowActivityMain(applicationContext, tvShow)
    }

    override fun onTvShowLongPressed() {
        Toast.makeText(this, "LONG PRESSED", Toast.LENGTH_SHORT).show()
    }

    override fun showError() {
        Toast.makeText(this, "ERROR: CHECK INTERNET CONNECTION", Toast.LENGTH_SHORT).show()
    }
}