package com.ich.reciptopia.domain.use_case.search

data class SearchUseCases(
    val getFavorites: GetFavoritesUseCase,
    val deleteFavorite: DeleteFavoriteUseCase,
    val getSearchHistories: GetSearchHistoriesUseCase,
    val addSearchHistory: AddSearchHistoryUseCase,
    val deleteSearchHistory: DeleteSearchHistoryUseCase,
    val getPost: GetPostUseCase,
    val getOwner: GetOwnerOfPostUseCase
)
