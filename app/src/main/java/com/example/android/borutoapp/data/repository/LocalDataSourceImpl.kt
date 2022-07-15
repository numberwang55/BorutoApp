package com.example.android.borutoapp.data.repository

import com.example.android.borutoapp.data.local.BorutoDatabase
import com.example.android.borutoapp.domain.model.entity.HeroEntity
import com.example.android.borutoapp.domain.repository.LocalDataSource

class LocalDataSourceImpl(borutoDatabase: BorutoDatabase): LocalDataSource {

    private val heroDao = borutoDatabase.heroDao

    override suspend fun getHeroById(heroId: Int): HeroEntity {
        return heroDao.getHeroById(heroId)
    }
}