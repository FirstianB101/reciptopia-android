package com.ich.reciptopia.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.ich.reciptopia.common.util.ChipInfoListTypeConverter
import com.ich.reciptopia.data.data_source.SearchHistoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson{
        return Gson()
    }

    @Provides
    @Singleton
    fun provideSearchHistoryDatabase(app: Application, gson: Gson): SearchHistoryDatabase{
        return Room
            .databaseBuilder(app, SearchHistoryDatabase::class.java, SearchHistoryDatabase.DATABASE_NAME)
            .addTypeConverter(ChipInfoListTypeConverter(gson))
            .build()
    }
}