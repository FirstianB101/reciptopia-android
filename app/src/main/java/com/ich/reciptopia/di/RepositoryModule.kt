package com.ich.reciptopia.di

import com.ich.reciptopia.data.data_source.SearchHistoryDatabase
import com.ich.reciptopia.data.repository.SearchHistoryRepositoryImpl
import com.ich.reciptopia.domain.repository.SearchHistoryRepository
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
    fun provideSearchHistoryRepository(db: SearchHistoryDatabase): SearchHistoryRepository{
        return SearchHistoryRepositoryImpl(db.searchHistoryDao)
    }
}