package com.ich.reciptopia.domain.use_case.search

import com.ich.reciptopia.domain.use_case.post.*

data class SearchUseCases(
    val deleteFavorite: DeleteFavoriteUseCase,
    val getSearchHistories: GetSearchHistoriesUseCase,
    val addSearchHistory: AddSearchHistoryUseCase,
    val deleteSearchHistory: DeleteSearchHistoryUseCase,
    val getPost: GetPostUseCase,
    val getOwner: GetOwnerOfPostUseCase,
    val getSearchedPosts: GetSearchedPostsUseCase,
    val favoritePost: FavoritePostUseCase,
    val unFavoritePost: UnFavoritePostUseCase,
    val getFavorites: GetFavoritesUseCase,
    val likePost: PostLikeUseCase,
    val unlikePost: PostUnLikeUseCase,
    val getPostLikeTags: GetPostLikeTagsUseCase,
)
