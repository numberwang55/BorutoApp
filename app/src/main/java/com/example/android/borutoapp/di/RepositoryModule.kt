package com.example.android.borutoapp.di

import android.app.Application
import com.example.android.borutoapp.data.repository.DataStoreOperationsImpl
import com.example.android.borutoapp.data.repository.Repository
import com.example.android.borutoapp.domain.repository.DataStoreOperations
import com.example.android.borutoapp.domain.use_cases.UseCases
import com.example.android.borutoapp.domain.use_cases.get_all_heroes.GetAllHeroesUseCase
import com.example.android.borutoapp.domain.use_cases.get_hero_by_id.GetHeroByIdUseCase
import com.example.android.borutoapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.example.android.borutoapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.example.android.borutoapp.domain.use_cases.search_hero.SearchHeroUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreOperations(app: Application): DataStoreOperations {
        return DataStoreOperationsImpl(app)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository),
            getAllHeroesUseCase = GetAllHeroesUseCase(repository),
            searchHeroUseCase = SearchHeroUseCase(repository),
            getHeroByIdUseCase = GetHeroByIdUseCase(repository)
        )
    }
}