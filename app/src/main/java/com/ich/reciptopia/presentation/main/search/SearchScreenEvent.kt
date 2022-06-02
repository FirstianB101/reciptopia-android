package com.ich.reciptopia.presentation.main.search

import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.presentation.community.CommunityScreenEvent
import com.ich.reciptopia.presentation.main.search.util.ChipState

sealed class SearchScreenEvent{
    data class DoSearch(val ingredients: List<ChipState>): SearchScreenEvent()
    data class ClickHistory(val history: SearchHistory): SearchScreenEvent()
    data class DeleteSearchHistory(val history: SearchHistory): SearchScreenEvent()
    data class DeleteFavorite(val favorite: Favorite): SearchScreenEvent()
    object GetSearchHistories: SearchScreenEvent()
    object GetFavoritePosts: SearchScreenEvent()

    object RefreshResultList: SearchScreenEvent()
    data class FavoriteButtonClicked(val post: Post, val idx: Int): SearchScreenEvent()
    data class LikeButtonClicked(val post: Post, val idx: Int): SearchScreenEvent()
}