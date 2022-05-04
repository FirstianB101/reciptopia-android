package com.ich.reciptopia.presentation.main.search

import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.presentation.main.search.util.ChipInfo

data class SearchState(
    val searchHistories: List<SearchHistory> = emptyList(),
    val isLoading: Boolean = false,
    val chipInfosForSearch: List<ChipInfo>? = null,
    val chipText: String = "",
    val searchMode: Boolean = false,
    val searchQuery: String = ""
)
