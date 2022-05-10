package com.ich.reciptopia.di

import com.ich.reciptopia.data.data_source.SearchHistoryDatabase
import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.data.repository.LoginRepositoryImpl
import com.ich.reciptopia.data.repository.SearchHistoryRepositoryImpl
import com.ich.reciptopia.data.repository.SignUpRepositoryImpl
import com.ich.reciptopia.domain.repository.LoginRepository
import com.ich.reciptopia.domain.repository.SearchHistoryRepository
import com.ich.reciptopia.domain.repository.SignUpRepository
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

    @Provides
    @Singleton
    fun provideLoginRepository(api: ReciptopiaApi): LoginRepository{
        return LoginRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSignUpRepository(api: ReciptopiaApi): SignUpRepository{
        return SignUpRepositoryImpl(api)
    }
}