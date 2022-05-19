package com.ich.reciptopia.presentation.main.search

import com.ich.reciptopia.domain.model.*
import com.ich.reciptopia.presentation.main.search.util.ChipInfo

data class SearchState(
    val isLoading: Boolean = false,
    val searchHistories: List<SearchHistory> = emptyList(),
    val favorites: List<Favorite> = emptyList(),
    val chipInfosForSearch: List<ChipInfo>? = null,
    val posts: List<Post> = emptyList(),
    val likeTags: List<PostLikeTag> = emptyList(),
    val currentUser: User? = null
)
