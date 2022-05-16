package com.ich.reciptopia.presentation.main.search

import com.ich.reciptopia.domain.model.FavoriteEntity
import com.ich.reciptopia.domain.model.SearchHistoryEntity

sealed class SearchScreenEvent{
    data class AddSearchHistoryEntity(val historyEntity: SearchHistoryEntity): SearchScreenEvent()
    data class DeleteSearchHistoryEntity(val historyEntity: SearchHistoryEntity): SearchScreenEvent()
    data class DeleteFavoriteEntity(val favoriteEntity: FavoriteEntity): SearchScreenEvent()
}