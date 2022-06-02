package com.ich.reciptopia.presentation.main.search

import com.ich.reciptopia.domain.model.*
import com.ich.reciptopia.presentation.main.search.util.ChipState

data class SearchState(
    val isLoading: Boolean = false,
    val searchHistories: List<SearchHistory> = emptyList(),
    val favorites: List<Favorite> = emptyList(),
    val chipsForSearch: List<ChipState>? = null,
    val posts: List<Post> = emptyList(),
    val likeTags: List<PostLikeTag> = emptyList(),
    val currentUser: User? = null
)
