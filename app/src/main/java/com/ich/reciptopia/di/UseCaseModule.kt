package com.ich.reciptopia.di

import com.ich.reciptopia.domain.repository.*
import com.ich.reciptopia.domain.use_case.community.*
import com.ich.reciptopia.domain.use_case.my_page.login.LoginUseCase
import com.ich.reciptopia.domain.use_case.my_page.profile.NicknameChangeUseCase
import com.ich.reciptopia.domain.use_case.my_page.sign_up.CreateAccountUseCase
import com.ich.reciptopia.domain.use_case.my_page.sign_up.EmailExistsUseCase
import com.ich.reciptopia.domain.use_case.my_page.sign_up.SignUpUseCases
import com.ich.reciptopia.domain.use_case.post_detail.*
import com.ich.reciptopia.domain.use_case.search.*
import com.ich.reciptopia.domain.use_case.search.GetOwnerOfPostUseCase
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
    fun provideSearchHistoryUseCases(repository: SearchRepository): SearchUseCases{
        return SearchUseCases(
            getFavorites = com.ich.reciptopia.domain.use_case.search.GetFavoritesUseCase(repository),
            deleteFavorite = DeleteFavoriteUseCase(repository),
            getSearchHistories = GetSearchHistoriesUseCase(repository),
            addSearchHistory = AddSearchHistoryUseCase(repository),
            deleteSearchHistory = DeleteSearchHistoryUseCase(repository),
            getPost = GetPostUseCase(repository),
            getOwner = GetOwnerOfPostUseCase(repository)
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

    @Provides
    @Singleton
    fun provideCommunityUseCases(repository: CommunityRepository): CommunityUseCases{
        return CommunityUseCases(
            createPost = CreatePostUseCase(repository),
            getPostsByTime = GetPostsByTimeUseCase(repository),
            getPostsByViews = GetPostsByViewsUseCase(repository),
            getPostLikeTags = GetPostLikeTagsUseCase(repository),
            postLike = PostLikeUseCase(repository),
            getOwnerOfPost = com.ich.reciptopia.domain.use_case.community.GetOwnerOfPostUseCase(repository),
            favoritePost = FavoritePostUseCase(repository),
            getFavorites = com.ich.reciptopia.domain.use_case.community.GetFavoritesUseCase(repository),
            unFavoritePost = UnFavoritePostUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun providePostDetailUseCases(repository: PostDetailRepository): PostDetailUseCases{
        return PostDetailUseCases(
            getPostInfo = GetPostInfoUseCase(repository),
            getOwnerOfPost = GetOwnerOfPostDetailUseCase(repository),
            favoritePostNotLogin = FavoritePostDetailNotLoginUseCase(repository),
            unFavoritePostNotLogin = UnFavoritePostDetailNotLoginUseCase(repository),
            getFavoritesFromDB = GetFavoritesFromDBUseCase(repository),
            getFavorites = com.ich.reciptopia.domain.use_case.post_detail.GetFavoritesUseCase(
                repository
            )
        )
    }
}