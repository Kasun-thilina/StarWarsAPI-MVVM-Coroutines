package com.kc.starwarsapidemo.presentation.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kc.starwarsapidemo.data.model.Planet
import com.kc.starwarsapidemo.data.repository.PlanetRepository

class PlanetPagingSource(private val planetRepository: PlanetRepository) :
    PagingSource<Int, Planet>() {
    override fun getRefreshKey(state: PagingState<Int, Planet>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Planet> {
        val position: Int = params.key ?: 1
        return try {
            val response = planetRepository.getPlanets(position)
            val nextKey = if (response.next == null) null else position + 1
            LoadResult.Page(
                data = response.results ?: mutableListOf(),
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}