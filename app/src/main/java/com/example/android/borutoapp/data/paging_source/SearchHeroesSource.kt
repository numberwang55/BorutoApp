package com.example.android.borutoapp.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android.borutoapp.data.remote.BorutoApi
import com.example.android.borutoapp.domain.model.entity.HeroEntity
import java.lang.Exception
import javax.inject.Inject

class SearchHeroesSource @Inject constructor(
    private val borutoApi: BorutoApi,
    private val query: String
): PagingSource<Int, HeroEntity>() {

    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, HeroEntity> {
        return try {
            val apiResponse = borutoApi.searchHero(name = query)
            val heroes = apiResponse.heroes
            if (heroes.isNotEmpty()) {
                LoadResult.Page(
                    data = heroes,
                    prevKey = apiResponse.previousPage,
                    nextKey = apiResponse.nextPage
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
           LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, HeroEntity>): Int? {
        return state.anchorPosition
    }
}