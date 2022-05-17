package com.ich.reciptopia.presentation.main.search

import com.ich.reciptopia.domain.model.FavoriteEntity
import com.ich.reciptopia.domain.model.SearchHistoryEntity
import com.ich.reciptopia.domain.model.User
import com.ich.reciptopia.presentation.main.search.util.ChipInfo

data class SearchState(
    val searchHistoryEntities: List<SearchHistoryEntity> = emptyList(),
    val favoriteEntities: List<FavoriteEntity> = emptyList(),
    val isLoading: Boolean = false,
    val chipInfosForSearch: List<ChipInfo>? = null,
    val currentUser: User? = null
)
