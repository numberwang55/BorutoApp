package com.example.android.borutoapp.data.paging_source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.android.borutoapp.data.local.BorutoDatabase
import com.example.android.borutoapp.domain.model.entity.HeroEntity
import com.example.android.borutoapp.domain.model.entity.HeroRemoteKeys
import com.example.android.borutoapp.data.remote.BorutoApi
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class HeroRemoteMediator @Inject constructor(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase
): RemoteMediator<Int, HeroEntity>() {

    private val heroDao = borutoDatabase.heroDao
    private val heroRemoteKeysDao = borutoDatabase.heroRemoteKeysDao

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = heroRemoteKeysDao.getRemoteKeys(heroId = 1)?.lastUpdated ?: 0L
        val cacheTimeout = 1440
        Log.d("RemoteMediator", "current time: ${parseMillis(currentTime)}")
        Log.d("RemoteMediator", "lastUpdated time: ${parseMillis(lastUpdated)}")

        val differenceInMinutes = (currentTime - lastUpdated) / 1000 / 60
        return if (differenceInMinutes.toInt() <= cacheTimeout) {
            Log.d("RemoteMediator", "Up-to-date!")
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            Log.d("RemoteMediator", "Refresh!")
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HeroEntity>
    ): RemoteMediator.MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirsItem(state)
                    val previousPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    previousPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }
            val response = borutoApi.getAllHeroes(page = page)
            if (response.heroes.isNotEmpty()) {
                borutoDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        heroDao.deleteAllHeroes()
                        heroRemoteKeysDao.deleteAllRemoteKeys()
                    }
                    val previousPage = response.previousPage
                    val nextPage = response.nextPage
                    val keys = response.heroes.map { heroEntity ->
                        HeroRemoteKeys(
                            id = heroEntity.id,
                            prevPage = previousPage,
                            nextPage = nextPage,
                            lastUpdated = response.lastUpdated
                        )
                    }
                    heroRemoteKeysDao.addAllRemoteKeys(heroRemoteKeys =  keys)
                    heroDao.addHeroes(heroes = response.heroes)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, HeroEntity>
    ): HeroRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                heroRemoteKeysDao.getRemoteKeys(heroId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirsItem(
        state: PagingState<Int, HeroEntity>
    ): HeroRemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { heroEntity ->
            heroRemoteKeysDao.getRemoteKeys(heroId = heroEntity.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, HeroEntity>
    ): HeroRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { heroEntity ->
                heroRemoteKeysDao.getRemoteKeys(heroId = heroEntity.id)
            }
    }

        private fun parseMillis(millis: Long): String {
            val date = Date(millis)
            val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ROOT)
            return format.format(date)
        }

}