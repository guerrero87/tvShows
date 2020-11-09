package com.example.kotlintvshows.utils

import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlintvshows.adapter.TmdbAdapter

fun detectRecyclerScrollPosition(dy: Int,
                                 gridLayoutManager: GridLayoutManager,
                                 adapter: TmdbAdapter): Boolean {
    return if (dy > 0) {
        val visibleItemCount: Int = gridLayoutManager.childCount
        val pastVisibleItem: Int = gridLayoutManager.findFirstCompletelyVisibleItemPosition()
        val total: Int = adapter.itemCount

        ((visibleItemCount + pastVisibleItem) > total)
    } else {
        false
    }
}