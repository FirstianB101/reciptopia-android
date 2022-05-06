package com.ich.reciptopia.presentation.main.search

import com.ich.reciptopia.domain.model.SearchHistoryEntity
import com.ich.reciptopia.presentation.main.search.util.ChipInfo

data class SearchState(
    val searchHistoryEntities: List<SearchHistoryEntity> = emptyList(),
    val isLoading: Boolean = false,
    val chipInfosForSearch: List<ChipInfo>? = null
)
