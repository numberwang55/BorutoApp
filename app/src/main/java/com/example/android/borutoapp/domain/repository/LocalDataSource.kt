package com.example.android.borutoapp.domain.repository

import com.example.android.borutoapp.domain.model.entity.HeroEntity

interface LocalDataSource {

    suspend fun getHeroById(heroId: Int): HeroEntity
}