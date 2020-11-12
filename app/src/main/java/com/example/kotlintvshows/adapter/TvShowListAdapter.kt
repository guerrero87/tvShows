package com.example.kotlintvshows.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintvshows.R
import com.example.kotlintvshows.interfaces.TvShowListContract
import com.example.kotlintvshows.tmdbAPI.model.TvShow
import com.example.kotlintvshows.utils.addTvShowToFavourites
import com.example.kotlintvshows.utils.openUserDataFile
import com.example.kotlintvshows.utils.removeTvShowToFavourites
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_tvshow.view.*

class TvShowListAdapter (private val context: Context,
                         private val tvShowsList: ArrayList<TvShow>,
                         private val tvShowListContract: TvShowListContract.View):
    RecyclerView.Adapter<TvShowListAdapter.TvShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tvshow, parent, false)
        return TvShowViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tvShowsList.size
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.bind(context, tvShowsList[position], tvShowListContract)
    }

    class TvShowViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(context: Context, tvShow: TvShow, tvShowListContract: TvShowListContract.View){
            Picasso
                .get()
                .load("https://image.tmdb.org/t/p/w500/${tvShow.poster_path}")
                .fit()
                .centerCrop()
                .into(itemView.imgPoster)

            //TODO: THIS ITERATION MIGHT REDUCE PERFORMANCE
            if (openUserDataFile(context).contains(tvShow.id)) {
                itemView.imgFavTvShow.visibility = View.VISIBLE
            }

            itemView.tvName.text = tvShow.name

            itemView.setOnClickListener{
                tvShowListContract.onTvShowPressed(tvShow)
            }

            itemView.setOnLongClickListener {
                if (itemView.imgFavTvShow.visibility == View.VISIBLE) {
                    tvShowListContract.showToast(context.getString(R.string.show_already_fav))
                    true
                } else {
                    itemView.imgFavTvShow.visibility = View.VISIBLE
                    addTvShowToFavourites(context, tvShow.id)
                    tvShowListContract.showToast(context.getString(R.string.show_added))
                    true
                }
            }
            itemView.imgFavTvShow.setOnClickListener {
                itemView.imgFavTvShow.visibility = View.GONE
                removeTvShowToFavourites(context, tvShow.id)
                tvShowListContract.showToast(context.getString(R.string.show_removed))
            }
        }
    }
}