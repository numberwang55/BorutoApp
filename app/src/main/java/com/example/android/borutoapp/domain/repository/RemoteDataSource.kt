package com.example.android.borutoapp.domain.repository

import androidx.paging.PagingData
import com.example.android.borutoapp.domain.model.entity.HeroEntity
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getAllHeroes(): Flow<PagingData<HeroEntity>>
    fun searchHeroes(query: String): Flow<PagingData<HeroEntity>>
}