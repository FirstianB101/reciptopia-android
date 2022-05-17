package com.ich.reciptopia.presentation.main.search

import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.domain.model.User
import com.ich.reciptopia.presentation.main.search.util.ChipInfo

data class SearchState(
    val searchHistories: List<SearchHistory> = emptyList(),
    val favorites: List<Favorite> = emptyList(),
    val isLoading: Boolean = false,
    val chipInfosForSearch: List<ChipInfo>? = null,
    val currentUser: User? = null
)
