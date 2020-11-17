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

    private var favTvShowsList: ArrayList<TvShow> = ArrayList()
    private lateinit var favTvShowsAdapter: MainAdapter

    override fun createPresenter(context: Context): MainPresenter {
        return MainPresenter(this, TmdbManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchFavTvShowsDetails()
        initListeners()
    }

    private fun initListeners() {
        btnPopularTvSHows.setOnClickListener {
            finish()
            launchTvShowsListActivity(this, btnPopularTvSHows.text as String)
        }
        btnTopTvSHows.setOnClickListener {
            finish()
            launchTvShowsListActivity(this, btnTopTvSHows.text as String)
        }
    }

    private fun initFavShowsRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL,
            false)
        favTvShowsAdapter = MainAdapter(this, favTvShowsList, this)

        recyclerFavShows.layoutManager = linearLayoutManager
        recyclerFavShows.adapter = favTvShowsAdapter
        favTvShowsAdapter.notifyDataSetChanged()
    }

    override fun fetchFavTvShowsDetails() {
        val favTvShowsIdList: MutableList<Int> = createOrOpenUserDataFile(this)
        val deviceLocale: String = Locale.getDefault().language

        for (tvShowId in favTvShowsIdList) {
            presenter.fetchSingleTvShowData(this, tvShowId, deviceLocale)
        }
    }

    override fun showSingleTvShowResponseDetails(tvShow: TvShow) {
        favTvShowsList.add(tvShow)
        initFavShowsRecyclerView()
    }

    override fun onTvShowPressed(tvShowId: Int) {
        finish()
        launchTvShowActivityMain(applicationContext, tvShowId)
    }

    override fun refreshRecycler(tvShow: TvShow) {
        favTvShowsList.remove(tvShow)
        favTvShowsAdapter.notifyDataSetChanged()
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}