package com.example.android.borutoapp.domain.use_cases.search_hero

import androidx.paging.PagingData
import com.example.android.borutoapp.data.repository.Repository
import com.example.android.borutoapp.domain.model.entity.HeroEntity
import kotlinx.coroutines.flow.Flow

class SearchHeroUseCase(
    private val repository: Repository
) {
    operator fun invoke(query: String): Flow<PagingData<HeroEntity>> {
        return repository.searchHeroes(query)
    }
}