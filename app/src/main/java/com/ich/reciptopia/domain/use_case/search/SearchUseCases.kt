package com.ich.reciptopia.domain.use_case.search

data class SearchUseCases(
    val getSearchHistoryEntities: GetSearchHistoryEntities,
    val addSearchHistoryEntity: AddSearchHistoryEntity,
    val deleteSearchHistoryEntity: DeleteSearchHistoryEntity,
    val getFavoriteEntities: GetFavoriteEntities,
    val deleteFavoriteEntity: DeleteFavoriteEntity
)
