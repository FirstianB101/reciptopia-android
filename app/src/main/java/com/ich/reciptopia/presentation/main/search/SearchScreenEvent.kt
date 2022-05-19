package com.ich.reciptopia.presentation.main.search

import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.SearchHistory

sealed class SearchScreenEvent{
    data class DoSearch(val ingredientNames: List<String?>): SearchScreenEvent()
    data class ClickHistory(val history: SearchHistory): SearchScreenEvent()
    data class DeleteSearchHistory(val history: SearchHistory): SearchScreenEvent()
    data class DeleteFavorite(val favorite: Favorite): SearchScreenEvent()
    object GetSearchHistories: SearchScreenEvent()
    object GetFavoritePosts: SearchScreenEvent()

    data class FavoriteButtonClicked(val post: Post): SearchScreenEvent()
}