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
import com.ich.reciptopia.domain.use_case.post.*
import com.ich.reciptopia.domain.use_case.post_detail.*
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
    fun provideSearchUseCases(searchRepository: SearchRepository, postRepository: PostRepository): SearchUseCases{
        return SearchUseCases(
            deleteFavorite = DeleteFavoriteUseCase(postRepository),
            getSearchHistories = GetSearchHistoriesUseCase(searchRepository),
            addSearchHistory = AddSearchHistoryUseCase(searchRepository),
            deleteSearchHistory = DeleteSearchHistoryUseCase(searchRepository),
            getPost = GetPostUseCase(searchRepository),
            getOwner = GetOwnerOfPostUseCase(postRepository),
            getSearchedPosts = GetSearchedPostsUseCase(searchRepository),
            getFavorites = GetFavoritesUseCase(postRepository),
            favoritePost = FavoritePostUseCase(postRepository),
            unFavoritePost = UnFavoritePostUseCase(postRepository),
            likePost = PostLikeUseCase(postRepository),
            unlikePost = PostUnLikeUseCase(postRepository),
            getPostLikeTags = GetPostLikeTagsUseCase(postRepository)
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
    fun provideCommunityUseCases(communityRepository: CommunityRepository, postRepository: PostRepository): CommunityUseCases{
        return CommunityUseCases(
            createPost = CreatePostUseCase(communityRepository),
            getPostsByTime = GetPostsByTimeUseCase(communityRepository),
            getPostsByViews = GetPostsByViewsUseCase(communityRepository),
            getPostLikeTags = GetPostLikeTagsUseCase(postRepository),
            getOwnerOfPost = GetOwnerOfPostUseCase(postRepository),
            favoritePost = FavoritePostUseCase(postRepository),
            getFavorites = GetFavoritesUseCase(postRepository),
            unFavoritePost = UnFavoritePostUseCase(postRepository),
            likePost = PostLikeUseCase(postRepository),
            unlikePost = PostUnLikeUseCase(postRepository)
        )
    }

    @Provides
    @Singleton
    fun providePostDetailUseCases(postDetailRepository: PostDetailRepository, postRepository: PostRepository): PostDetailUseCases{
        return PostDetailUseCases(
            getPostInfo = GetPostInfoUseCase(postDetailRepository),
            getOwnerOfPost = GetOwnerOfPostUseCase(postRepository),
            favoritePost = FavoritePostUseCase(postRepository),
            unFavoritePost = UnFavoritePostUseCase(postRepository),
            getFavorites = GetFavoritesUseCase(postRepository),
            likePost = PostLikeUseCase(postRepository),
            unlikePost = PostUnLikeUseCase(postRepository),
            getLikeTags = GetPostLikeTagsUseCase(postRepository),
            getRecipe = GetRecipeUseCase(postDetailRepository),
            getComments = GetCommentsUseCase(postDetailRepository),
            getMainIngredients = GetMainIngredientsUseCase(postDetailRepository),
            getReplies = GetRepliesUseCase(postDetailRepository),
            getSteps = GetStepsUseCase(postDetailRepository),
            getSubIngredients = GetSubIngredientsUseCase(postDetailRepository)
        )
    }
}