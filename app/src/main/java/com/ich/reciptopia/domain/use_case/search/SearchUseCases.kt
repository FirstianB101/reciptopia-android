package com.ich.reciptopia.domain.use_case.search

data class SearchUseCases(
    val getSearchHistoriesFromDB: GetSearchHistoriesFromDBUseCase,
    val addSearchHistoryInDB: AddSearchHistoryInDBUseCase,
    val deleteSearchHistoryFromDB: DeleteSearchHistoryFromDBUseCase,
    val getFavoritesFromDB: GetFavoritesFromDBUseCase,
    val deleteFavoriteFromDB: DeleteFavoriteFromDBUseCase,
    val getSearchHistories: GetSearchHistoriesUseCase,
    val addSearchHistory: AddSearchHistoryUseCase,
    val deleteSearchHistory: DeleteSearchHistoryUseCase,
    val getPost: GetPostUseCase,
    val getOwner: GetOwnerOfPostUseCase
)
