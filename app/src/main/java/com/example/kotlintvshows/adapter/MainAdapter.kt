package com.example.kotlintvshows.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintvshows.R
import com.example.kotlintvshows.presenter.MainPresenter
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_tvshow.view.*

class MainAdapter (private val tvShowsList: ArrayList<TvShow>, private val mainPresenter: MainPresenter):
    RecyclerView.Adapter<MainAdapter.TvShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tvshow, parent, false)
        return TvShowViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tvShowsList.size
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.bind(tvShowsList[position], mainPresenter)
    }

    class TvShowViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(tvShow: TvShow, mainPresenter: MainPresenter){
            Picasso
                .get()
                .load("https://image.tmdb.org/t/p/w500/${tvShow.poster_path}")
                .fit()
                .centerCrop()
                .into(itemView.imgPoster)

            itemView.tvName.text = tvShow.name

            itemView.setOnClickListener{
                mainPresenter.onTvShowPressed(tvShow)
            }
            itemView.setOnLongClickListener {
                itemView.imgFavTvShow.visibility = View.VISIBLE
                mainPresenter.onTvShowLongPressed()
                true
            }
            itemView.imgFavTvShow.setOnClickListener {
                itemView.imgFavTvShow.visibility = View.GONE
            }
        }
    }
}

