package com.ich.reciptopia.presentation.main.search

import com.ich.reciptopia.domain.model.SearchHistory

sealed class SearchScreenEvent{
    data class AddSearchHistory(val history: SearchHistory): SearchScreenEvent()
    data class DeleteSearchHistory(val history: SearchHistory): SearchScreenEvent()
}