package com.example.android.borutoapp.di

import android.app.Application
import androidx.room.Room
import com.example.android.borutoapp.data.local.BorutoDatabase
import com.example.android.borutoapp.data.repository.DataStoreOperationsImpl
import com.example.android.borutoapp.data.repository.LocalDataSourceImpl
import com.example.android.borutoapp.data.repository.Repository
import com.example.android.borutoapp.domain.repository.DataStoreOperations
import com.example.android.borutoapp.domain.repository.LocalDataSource
import com.example.android.borutoapp.domain.use_cases.UseCases
import com.example.android.borutoapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.example.android.borutoapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.example.android.borutoapp.util.Constants.BORUTO_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideBorutoDatabase(app: Application): BorutoDatabase {
        return Room.databaseBuilder(
            app,
            BorutoDatabase::class.java,
            BORUTO_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(borutoDatabase: BorutoDatabase): LocalDataSource {
        return LocalDataSourceImpl(borutoDatabase)
    }

}