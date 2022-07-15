package com.example.android.borutoapp.domain.use_cases.get_all_heroes

import androidx.paging.PagingData
import com.example.android.borutoapp.data.repository.Repository
import com.example.android.borutoapp.domain.model.entity.HeroEntity
import kotlinx.coroutines.flow.Flow

class GetAllHeroesUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<PagingData<HeroEntity>> {
        return repository.getAllHeroes()
    }
}