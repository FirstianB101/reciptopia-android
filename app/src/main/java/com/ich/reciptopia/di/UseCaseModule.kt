package com.ich.reciptopia.di

import com.ich.reciptopia.domain.repository.*
import com.ich.reciptopia.domain.use_case.community.*
import com.ich.reciptopia.domain.use_case.my_page.login.LoginUseCase
import com.ich.reciptopia.domain.use_case.my_page.profile.NicknameChangeUseCase
import com.ich.reciptopia.domain.use_case.my_page.sign_up.CreateAccountUseCase
import com.ich.reciptopia.domain.use_case.my_page.sign_up.EmailExistsUseCase
import com.ich.reciptopia.domain.use_case.my_page.sign_up.SignUpUseCases
import com.ich.reciptopia.domain.use_case.post_detail.GetPostInfoUseCase
import com.ich.reciptopia.domain.use_case.post_detail.PostDetailUseCases
import com.ich.reciptopia.domain.use_case.search.*
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
            getSearchHistoryEntities = GetSearchHistoryEntities(repository),
            addSearchHistoryEntity = AddSearchHistoryEntity(repository),
            deleteSearchHistoryEntity = DeleteSearchHistoryEntity(repository),
            getFavoriteEntities = GetFavoriteEntities(repository),
            deleteFavoriteEntity = DeleteFavoriteEntity(repository)
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
            getOwnerOfPost = GetOwnerOfPostUseCase(repository),
            favoritePostNotLogin = FavoritePostNotLoginUseCase(repository),
            getFavoriteEntities = GetFavoriteEntitiesUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun providePostDetailUseCases(repository: PostDetailRepository): PostDetailUseCases{
        return PostDetailUseCases(
            getPostInfo = GetPostInfoUseCase(repository)
        )
    }
}