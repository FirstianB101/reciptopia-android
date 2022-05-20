package com.ich.reciptopia.domain.use_case.community

import com.ich.reciptopia.domain.use_case.post_list.*

data class CommunityUseCases(
    val createPost: CreatePostUseCase,
    val getPostsByTime: GetPostsByTimeUseCase,
    val getPostsByViews: GetPostsByViewsUseCase,
    val getOwnerOfPost: GetOwnerOfPostUseCase,
    val favoritePost: FavoritePostUseCase,
    val unFavoritePost: UnFavoritePostUseCase,
    val getFavorites: GetFavoritesUseCase,
    val likePost: PostLikeUseCase,
    val unlikePost: PostUnLikeUseCase,
    val getPostLikeTags: GetPostLikeTagsUseCase,
)