package com.example.kotlintvshows.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintvshows.R
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_tvshow.view.*

class TmdbAdapter (private val tvShowsList: ArrayList<TvShow>):
    RecyclerView.Adapter<TmdbAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tvshow, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tvShowsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tvShowsList[position])
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(tvShow: TvShow){
            Picasso
                .get()
                .load("https://image.tmdb.org/t/p/w500/${tvShow.poster_path}")
                .resize(500, 750)
                .into(itemView.imgPoster)

            itemView.tvName.text = tvShow.name
        }
    }

}