package com.ich.reciptopia.domain.use_case.community

import com.ich.reciptopia.domain.use_case.post.*

data class CommunityUseCases(
    val createRecipePost: CreatePostUseCase,
    val getPostsByTime: GetPostsByTimeUseCase,
    val getPostsByViews: GetPostsByViewsUseCase,
    val getOwnerOfPost: GetOwnerByIdUseCase,
    val favoritePost: FavoritePostUseCase,
    val unFavoritePost: UnFavoritePostUseCase,
    val getFavorites: GetFavoritesUseCase,
    val likePost: PostLikeUseCase,
    val unlikePost: PostUnLikeUseCase,
    val getPostLikeTags: GetPostLikeTagsUseCase,
    val getOwnerProfileImage: GetOwnerProfileImageUseCase,
    val uploadPostImageUseCase: UploadPostImageUseCase,
    val uploadStepImageUseCase: UploadStepImageUseCase
)