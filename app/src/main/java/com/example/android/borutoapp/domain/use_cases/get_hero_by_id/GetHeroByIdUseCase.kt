package com.example.android.borutoapp.domain.use_cases.get_hero_by_id

import com.example.android.borutoapp.data.repository.Repository
import com.example.android.borutoapp.domain.model.entity.HeroEntity

class GetHeroByIdUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(heroId: Int): HeroEntity {
        return repository.getHeroById(heroId)
    }
}