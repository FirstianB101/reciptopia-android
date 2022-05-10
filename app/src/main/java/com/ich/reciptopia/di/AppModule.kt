package com.ich.reciptopia.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.common.util.ChipInfoListTypeConverter
import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.data.data_source.SearchHistoryDatabase
import com.ich.reciptopia.data.remote.AuthenticationInterceptor
import com.ich.reciptopia.data.remote.ReciptopiaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideApp() = ReciptopiaApplication.instance!!

    @Provides
    @Singleton
    fun provideAuthInterceptor() = AuthenticationInterceptor()

    @Provides
    fun provideAuthInterceptorOkHttpClient(
        authInterceptor: AuthenticationInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideReciptopiaRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideReciptopiaService(retrofit: Retrofit): ReciptopiaApi = retrofit.create(ReciptopiaApi::class.java)

    @Provides
    @Singleton
    fun provideSearchHistoryDatabase(app: Application, gson: Gson): SearchHistoryDatabase{
        return Room
            .databaseBuilder(app, SearchHistoryDatabase::class.java, SearchHistoryDatabase.DATABASE_NAME)
            .addTypeConverter(ChipInfoListTypeConverter(gson))
            .build()
    }
}