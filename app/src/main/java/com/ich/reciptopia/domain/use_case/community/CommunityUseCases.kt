package com.ich.reciptopia.domain.use_case.community

data class CommunityUseCases(
    val createPost: CreatePostUseCase,
    val getPostsByTime: GetPostsByTimeUseCase,
    val getPostsByViews: GetPostsByViewsUseCase,
    val getPostLikeTags: GetPostLikeTagsUseCase,
    val postLike: PostLikeUseCase,
    val getOwnerOfPost: GetOwnerOfPostUseCase,
    val favoritePost: FavoritePostUseCase,
    val unFavoritePost: UnFavoritePostUseCase,
    val getFavorites: GetFavoritesUseCase
)