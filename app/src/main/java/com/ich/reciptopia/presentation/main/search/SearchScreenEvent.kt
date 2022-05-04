package com.ich.reciptopia.presentation.main.search

import com.ich.reciptopia.domain.model.SearchHistory

sealed class SearchScreenEvent{
    data class AddSearchHistory(val history: SearchHistory): SearchScreenEvent()
    data class DeleteSearchHistory(val history: SearchHistory): SearchScreenEvent()
    data class ChipTextChanged(val text: String): SearchScreenEvent()
    data class SearchQueryChanged(val query: String): SearchScreenEvent()
    data class SearchModeChanged(val isOn: Boolean): SearchScreenEvent()
}