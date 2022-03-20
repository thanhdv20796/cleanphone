package com.example.cleanphone.di

import android.content.Context
import androidx.room.Room
import com.example.cleanphone.data.db.AppDatabase
import com.example.cleanphone.data.db.DBHelper
import com.example.cleanphone.data.db.DBHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): DBHelper {

        val appDatabase = AppDatabase.getInstance(context)
        return DBHelperImpl(appDatabase)
    }


}
