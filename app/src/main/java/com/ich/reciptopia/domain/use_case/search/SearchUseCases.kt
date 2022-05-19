package com.ich.reciptopia.domain.use_case.search

data class SearchUseCases(
    val getFavoritePosts: GetFavoritePostsUseCase,
    val deleteFavorite: DeleteFavoriteUseCase,
    val getSearchHistories: GetSearchHistoriesUseCase,
    val addSearchHistory: AddSearchHistoryUseCase,
    val deleteSearchHistory: DeleteSearchHistoryUseCase,
    val getPost: GetPostUseCase,
    val getOwner: GetOwnerOfPostUseCase,
    val getSearchedPosts: GetSearchedPostsUseCase,
    val favoritePost: FavoritePostUseCase,
    val unFavoritePost: UnFavoritePostUseCase,
    val getFavorites: GetFavoritesUseCase
)
