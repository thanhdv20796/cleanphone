package com.example.cleanphone.di

import com.example.cleanphone.ui.battery.data.BatterySaverRepositoryHelper
import com.example.cleanphone.ui.battery.data.BatterySaverRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideDataRepository(dataRepositoryImpl: BatterySaverRepositoryImp): BatterySaverRepositoryHelper
}