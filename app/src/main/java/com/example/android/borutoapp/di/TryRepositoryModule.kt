package com.example.android.borutoapp.di

import com.example.android.borutoapp.data.repository.DataStoreOperationsImpl
import com.example.android.borutoapp.data.repository.Repository
import com.example.android.borutoapp.domain.repository.DataStoreOperations
import com.example.android.borutoapp.domain.use_cases.UseCases
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TryRepositoryModule {

//    @Binds
//    @Singleton
//    abstract fun bindDataStoreOperations(
//        dataStoreOperationsImpl: DataStoreOperationsImpl
//    ): DataStoreOperations

}