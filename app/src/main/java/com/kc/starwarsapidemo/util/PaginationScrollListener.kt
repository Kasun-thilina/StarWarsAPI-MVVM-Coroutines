package com.kc.starwarsapidemo.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(
    private val layoutManager: LinearLayoutManager,
    val itemsPerPage: Int
) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int
    ) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= itemsPerPage) {
                loadMoreItems()
            }
        }
    }

    override fun onScrollStateChanged(
        recyclerView: RecyclerView,
        newState: Int
    ) {
        super.onScrollStateChanged(recyclerView, newState)
    }

    protected abstract fun loadMoreItems()

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean
}