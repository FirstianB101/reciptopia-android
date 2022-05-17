package com.ich.reciptopia.presentation.main.search

import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.SearchHistory

sealed class SearchScreenEvent{
    data class AddSearchHistory(val ingredientNames: List<String?>): SearchScreenEvent()
    data class DeleteSearchHistory(val history: SearchHistory): SearchScreenEvent()
    data class DeleteFavorite(val favorite: Favorite): SearchScreenEvent()
    object GetSearchHistories: SearchScreenEvent()
    object GetFavoritePosts: SearchScreenEvent()
}