package com.example.android.borutoapp.domain.use_cases

import com.example.android.borutoapp.domain.use_cases.get_all_heroes.GetAllHeroesUseCase
import com.example.android.borutoapp.domain.use_cases.get_hero_by_id.GetHeroByIdUseCase
import com.example.android.borutoapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.example.android.borutoapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.example.android.borutoapp.domain.use_cases.search_hero.SearchHeroUseCase

data class UseCases(
    val saveOnBoardingUseCase: SaveOnBoardingUseCase,
    val readOnBoardingUseCase: ReadOnBoardingUseCase,
    val getAllHeroesUseCase: GetAllHeroesUseCase,
    val searchHeroUseCase: SearchHeroUseCase,
    val getHeroByIdUseCase: GetHeroByIdUseCase
)
