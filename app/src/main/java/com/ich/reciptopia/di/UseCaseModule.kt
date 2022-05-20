package com.ich.reciptopia.di

import com.ich.reciptopia.domain.repository.*
import com.ich.reciptopia.domain.use_case.community.CommunityUseCases
import com.ich.reciptopia.domain.use_case.community.CreatePostUseCase
import com.ich.reciptopia.domain.use_case.community.GetPostsByTimeUseCase
import com.ich.reciptopia.domain.use_case.community.GetPostsByViewsUseCase
import com.ich.reciptopia.domain.use_case.my_page.login.LoginUseCase
import com.ich.reciptopia.domain.use_case.my_page.profile.NicknameChangeUseCase
import com.ich.reciptopia.domain.use_case.my_page.sign_up.CreateAccountUseCase
import com.ich.reciptopia.domain.use_case.my_page.sign_up.EmailExistsUseCase
import com.ich.reciptopia.domain.use_case.my_page.sign_up.SignUpUseCases
import com.ich.reciptopia.domain.use_case.post_detail.GetPostInfoUseCase
import com.ich.reciptopia.domain.use_case.post_detail.PostDetailUseCases
import com.ich.reciptopia.domain.use_case.post_list.*
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
    fun provideSearchUseCases(searchRepository: SearchRepository, postListRepository: PostListRepository): SearchUseCases{
        return SearchUseCases(
            deleteFavorite = DeleteFavoriteUseCase(postListRepository),
            getSearchHistories = GetSearchHistoriesUseCase(searchRepository),
            addSearchHistory = AddSearchHistoryUseCase(searchRepository),
            deleteSearchHistory = DeleteSearchHistoryUseCase(searchRepository),
            getPost = GetPostUseCase(searchRepository),
            getOwner = GetOwnerOfPostUseCase(postListRepository),
            getSearchedPosts = GetSearchedPostsUseCase(searchRepository),
            getFavorites = GetFavoritesUseCase(postListRepository),
            favoritePost = FavoritePostUseCase(postListRepository),
            unFavoritePost = UnFavoritePostUseCase(postListRepository),
            likePost = PostLikeUseCase(postListRepository),
            unlikePost = PostUnLikeUseCase(postListRepository),
            getPostLikeTags = GetPostLikeTagsUseCase(postListRepository)
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
    fun provideCommunityUseCases(communityRepository: CommunityRepository, postListRepository: PostListRepository): CommunityUseCases{
        return CommunityUseCases(
            createPost = CreatePostUseCase(communityRepository),
            getPostsByTime = GetPostsByTimeUseCase(communityRepository),
            getPostsByViews = GetPostsByViewsUseCase(communityRepository),
            getPostLikeTags = GetPostLikeTagsUseCase(postListRepository),
            getOwnerOfPost = GetOwnerOfPostUseCase(postListRepository),
            favoritePost = FavoritePostUseCase(postListRepository),
            getFavorites = GetFavoritesUseCase(postListRepository),
            unFavoritePost = UnFavoritePostUseCase(postListRepository),
            likePost = PostLikeUseCase(postListRepository),
            unlikePost = PostUnLikeUseCase(postListRepository)
        )
    }

    @Provides
    @Singleton
    fun providePostDetailUseCases(postDetailRepository: PostDetailRepository, postListRepository: PostListRepository): PostDetailUseCases{
        return PostDetailUseCases(
            getPostInfo = GetPostInfoUseCase(postDetailRepository),
            getOwnerOfPost = GetOwnerOfPostUseCase(postListRepository),
            favoritePost = FavoritePostUseCase(postListRepository),
            unFavoritePost = UnFavoritePostUseCase(postListRepository),
            getFavorites = GetFavoritesUseCase(postListRepository)
        )
    }
}