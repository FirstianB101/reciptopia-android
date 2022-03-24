package com.ich.reciptopia.di

import com.ich.reciptopia.domain.repository.SearchHistoryRepository
import com.ich.reciptopia.domain.use_case.search_history.AddSearchHistory
import com.ich.reciptopia.domain.use_case.search_history.DeleteSearchHistory
import com.ich.reciptopia.domain.use_case.search_history.GetSearchHistoriesInDB
import com.ich.reciptopia.domain.use_case.search_history.SearchHistoryUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideSearchHistoryUseCases(repository: SearchHistoryRepository): SearchHistoryUseCases{
        return SearchHistoryUseCases(
            getSearchHistories = GetSearchHistoriesInDB(repository),
            addSearchHistory = AddSearchHistory(repository),
            deleteSearchHistory = DeleteSearchHistory(repository)
        )
    }
}