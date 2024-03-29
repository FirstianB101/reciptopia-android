package com.ich.reciptopia.di

import com.ich.reciptopia.domain.repository.*
import com.ich.reciptopia.domain.use_case.community.*
import com.ich.reciptopia.domain.use_case.my_page.login.LoginUseCase
import com.ich.reciptopia.domain.use_case.my_page.profile.EditNicknameUseCase
import com.ich.reciptopia.domain.use_case.my_page.profile.GetAccountProfileImgUseCase
import com.ich.reciptopia.domain.use_case.my_page.profile.ProfileUseCases
import com.ich.reciptopia.domain.use_case.my_page.profile.UploadProfileImgUseCase
import com.ich.reciptopia.domain.use_case.my_page.sign_up.CreateAccountUseCase
import com.ich.reciptopia.domain.use_case.my_page.sign_up.EmailExistsUseCase
import com.ich.reciptopia.domain.use_case.my_page.sign_up.SignUpUseCases
import com.ich.reciptopia.domain.use_case.post.*
import com.ich.reciptopia.domain.use_case.post_detail.*
import com.ich.reciptopia.domain.use_case.post_detail.chat.*
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
            getOwner = GetOwnerByIdUseCase(postRepository),
            getSearchedPosts = GetSearchedPostsUseCase(searchRepository),
            getFavorites = GetFavoritesUseCase(postRepository),
            favoritePost = FavoritePostUseCase(postRepository),
            unFavoritePost = UnFavoritePostUseCase(postRepository),
            likePost = PostLikeUseCase(postRepository),
            unlikePost = PostUnLikeUseCase(postRepository),
            getPostLikeTags = GetPostLikeTagsUseCase(postRepository),
            getOwnerProfileImage = GetOwnerProfileImageUseCase(postRepository)
        )
    }

    @Provides
    @Singleton
    fun provideLoginUseCases(repository: LoginRepository): LoginUseCase{
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
    fun provideProfileUseCase(repository: ProfileRepository): EditNicknameUseCase{
        return EditNicknameUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCommunityUseCases(communityRepository: CommunityRepository, postRepository: PostRepository): CommunityUseCases{
        return CommunityUseCases(
            createRecipePost = CreatePostUseCase(communityRepository),
            getPostsByTime = GetPostsByTimeUseCase(communityRepository),
            getPostsByViews = GetPostsByViewsUseCase(communityRepository),
            getPostLikeTags = GetPostLikeTagsUseCase(postRepository),
            getOwnerOfPost = GetOwnerByIdUseCase(postRepository),
            favoritePost = FavoritePostUseCase(postRepository),
            getFavorites = GetFavoritesUseCase(postRepository),
            unFavoritePost = UnFavoritePostUseCase(postRepository),
            likePost = PostLikeUseCase(postRepository),
            unlikePost = PostUnLikeUseCase(postRepository),
            getOwnerProfileImage = GetOwnerProfileImageUseCase(postRepository),
            uploadPostImageUseCase = UploadPostImageUseCase(communityRepository),
            uploadStepImageUseCase = UploadStepImageUseCase(communityRepository)
        )
    }

    @Provides
    @Singleton
    fun providePostDetailUseCases(postDetailRepository: PostDetailRepository, postRepository: PostRepository): PostDetailUseCases{
        return PostDetailUseCases(
            getPostInfo = GetPostInfoUseCase(postDetailRepository),
            getOwnerById = GetOwnerByIdUseCase(postRepository),
            favoritePost = FavoritePostUseCase(postRepository),
            unFavoritePost = UnFavoritePostUseCase(postRepository),
            getFavorites = GetFavoritesUseCase(postRepository),
            likePost = PostLikeUseCase(postRepository),
            unlikePost = PostUnLikeUseCase(postRepository),
            getLikeTags = GetPostLikeTagsUseCase(postRepository),
            getRecipe = GetRecipeUseCase(postDetailRepository),
            getMainIngredients = GetMainIngredientsUseCase(postDetailRepository),
            getSteps = GetStepsUseCase(postDetailRepository),
            getSubIngredients = GetSubIngredientsUseCase(postDetailRepository),
            deletePost = DeletePostUseCase(postDetailRepository),
            getOwnerProfileImage = GetOwnerProfileImageUseCase(postRepository)
        )
    }

    @Provides
    @Singleton
    fun providePostDetailChatUseCases(repository: PostDetailChatRepository, postRepository: PostRepository): PostDetailChatUseCases{
        return PostDetailChatUseCases(
            getComments = GetCommentsUseCase(repository),
            getReplies = GetRepliesUseCase(repository),
            createComment = CreateCommentUseCase(repository),
            getCommentLikeTags = GetCommentLikeTagsUseCase(repository),
            getReplyLikeTags = GetReplyLikeTagsUseCase(repository),
            likeComment = LikeCommentUseCase(repository),
            unlikeComment = UnLikeCommentUseCase(repository),
            likeReply = LikeReplyUseCase(repository),
            unlikeReply = UnLikeReplyUseCase(repository),
            getOwner = GetCommentOwnerUseCase(repository),
            createReply = CreateReplyUseCase(repository),
            deleteComment = DeleteCommentUseCase(repository),
            deleteReply = DeleteReplyUseCase(repository),
            getOwnerProfileImage = GetOwnerProfileImageUseCase(postRepository)
        )
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases{
        return ProfileUseCases(
            editNickname = EditNicknameUseCase(repository),
            uploadProfileImg = UploadProfileImgUseCase(repository),
            getAccountProfileImg = GetAccountProfileImgUseCase(repository)
        )
    }
}