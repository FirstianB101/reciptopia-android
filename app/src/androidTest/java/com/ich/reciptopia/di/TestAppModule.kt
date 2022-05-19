package com.ich.reciptopia.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.ich.reciptopia.common.util.ChipInfoListTypeConverter
import com.ich.reciptopia.data.data_source.SearchHistoryDatabase
import com.ich.reciptopia.data.repository.SearchRepositoryImpl
import com.ich.reciptopia.domain.repository.SearchHistoryRepository
import com.ich.reciptopia.domain.use_case.search.SearchUseCases
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
        return SearchRepositoryImpl(db.searchHistoryDao)
    }

    @Provides
    @Singleton
    fun provideSearchHistoryUseCases(repository: SearchHistoryRepository): SearchUseCases {
        return SearchUseCases(
            getSearchHistoriesFromDB = GetSearchHistoriesFromDBUseCase(repository),
            addSearchHistoryInDB = AddSearchHistoryInDBUseCase(repository),
            deleteSearchHistoryFromDB = DeleteSearchHistoryFromDBUseCase(repository)
        )
    }
}