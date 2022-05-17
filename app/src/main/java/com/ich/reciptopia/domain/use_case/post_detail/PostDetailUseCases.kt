package com.ich.reciptopia.domain.use_case.post_detail

data class PostDetailUseCases(
    val getPostInfo: GetPostInfoUseCase,
    val getOwnerOfPost: GetOwnerOfPostDetailUseCase,
    val favoritePostNotLogin: FavoritePostDetailNotLoginUseCase,
    val unFavoritePostNotLogin: UnFavoritePostDetailNotLoginUseCase,
    val getFavoritePosts: GetFavoriteEntitiesUseCase
)
