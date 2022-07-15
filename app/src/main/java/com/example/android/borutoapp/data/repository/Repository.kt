package com.example.android.borutoapp.data.repository

import androidx.paging.PagingData
import com.example.android.borutoapp.domain.model.entity.HeroEntity
import com.example.android.borutoapp.domain.repository.DataStoreOperations
import com.example.android.borutoapp.domain.repository.LocalDataSource
import com.example.android.borutoapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val local: LocalDataSource,
    private val dataStore: DataStoreOperations,
    private val remoteDataSource: RemoteDataSource
) {

    fun getAllHeroes(): Flow<PagingData<HeroEntity>> {
        return remoteDataSource.getAllHeroes()
    }

    fun searchHeroes(query: String): Flow<PagingData<HeroEntity>> {
        return remoteDataSource.searchHeroes(query)
    }

    suspend fun getHeroById(heroId: Int): HeroEntity {
        return local.getHeroById(heroId)
    }

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed)
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }

}