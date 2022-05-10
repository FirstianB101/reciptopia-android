package com.ich.reciptopia.di

import com.ich.reciptopia.domain.repository.LoginRepository
import com.ich.reciptopia.domain.repository.ProfileRepository
import com.ich.reciptopia.domain.repository.SearchHistoryRepository
import com.ich.reciptopia.domain.repository.SignUpRepository
import com.ich.reciptopia.domain.use_case.my_page.login.LoginUseCase
import com.ich.reciptopia.domain.use_case.my_page.profile.NicknameChangeUseCase
import com.ich.reciptopia.domain.use_case.my_page.sign_up.CreateAccountUseCase
import com.ich.reciptopia.domain.use_case.my_page.sign_up.EmailExistsUseCase
import com.ich.reciptopia.domain.use_case.my_page.sign_up.SignUpUseCases
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

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: LoginRepository): LoginUseCase{
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSignUpUseCases(repository: SignUpRepository): SignUpUseCases{
        return SignUpUseCases(
            emailExists = EmailExistsUseCase(repository),
            createAccount = CreateAccountUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideProfileUseCase(repository: ProfileRepository): NicknameChangeUseCase{
        return NicknameChangeUseCase(repository)
    }
}