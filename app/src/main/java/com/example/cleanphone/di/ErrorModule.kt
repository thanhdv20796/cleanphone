package com.example.cleanphone.di

import com.example.cleanphone.data.model.error.ErrorManager
import com.example.cleanphone.data.model.error.mapper.ErrorMapperHelper
import com.example.cleanphone.data.model.error.mapper.ErrorMapperImpl
import com.ymo.data.model.error.ErrorUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorModule {
    @Binds
    @Singleton
    abstract fun provideErrorFactoryImpl(errorManager: ErrorManager): ErrorUseCase

    @Binds
    @Singleton
    abstract fun provideErrorMapper(errorMapperImpl: ErrorMapperImpl): ErrorMapperHelper
}
