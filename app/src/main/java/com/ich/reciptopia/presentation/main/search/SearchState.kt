package com.ich.reciptopia.presentation.main.search

import com.ich.reciptopia.domain.model.SearchHistory

sealed class SearchState{
    object Normal: SearchState()
    object Loading: SearchState()
    data class GetSearchHistory(val histories: List<SearchHistory>): SearchState()
    object AddSearchHistory: SearchState()
    object DeleteSearchHistory: SearchState()
}
