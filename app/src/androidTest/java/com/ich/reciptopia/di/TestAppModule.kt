package com.ich.reciptopia.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.ich.reciptopia.common.util.ChipInfoListTypeConverter
import com.ich.reciptopia.data.data_source.SearchHistoryDatabase
import com.ich.reciptopia.data.repository.SearchHistoryRepositoryImpl
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
object TestAppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson{
        return Gson()
    }

    @Provides
    @Singleton
    fun provideSearchHistoryDatabase(app: Application, gson: Gson): SearchHistoryDatabase{
        return Room
            .inMemoryDatabaseBuilder(app, SearchHistoryDatabase::class.java)
            .addTypeConverter(ChipInfoListTypeConverter(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideSearchHistoryRepository(db: SearchHistoryDatabase): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(db.searchHistoryDao)
    }

    @Provides
    @Singleton
    fun provideSearchHistoryUseCases(repository: SearchHistoryRepository): SearchHistoryUseCases {
        return SearchHistoryUseCases(
            getSearchHistories = GetSearchHistoriesInDB(repository),
            addSearchHistory = AddSearchHistory(repository),
            deleteSearchHistory = DeleteSearchHistory(repository)
        )
    }
}