package com.ich.reciptopia.di

import com.ich.reciptopia.data.data_source.ReciptopiaDatabase
import com.ich.reciptopia.data.remote.ImageAnalyzeApi
import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.data.repository.*
import com.ich.reciptopia.domain.repository.*
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
    fun provideSearchHistoryRepository(api: ReciptopiaApi, db: ReciptopiaDatabase): SearchRepository{
        return SearchRepositoryImpl(api, db.reciptopiaDao)
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

    @Provides
    @Singleton
    fun provideProfileRepository(api: ReciptopiaApi): ProfileRepository{
        return ProfileRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideCommunityRepository(api: ReciptopiaApi): CommunityRepository{
        return CommunityRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providePostDetailRepository(api: ReciptopiaApi): PostDetailRepository{
        return PostDetailRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providePostDetailChatRepository(api: ReciptopiaApi): PostDetailChatRepository{
        return PostDetailChatRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providePostListRepository(api: ReciptopiaApi, db: ReciptopiaDatabase): PostRepository{
        return PostRepositoryImpl(api, db.reciptopiaDao)
    }

    @Provides
    @Singleton
    fun provideAnalyzeIngredientRepository(api: ImageAnalyzeApi): AnalyzeIngredientRepository{
        return AnalyzeIngredientRepositoryImpl(api)
    }
}