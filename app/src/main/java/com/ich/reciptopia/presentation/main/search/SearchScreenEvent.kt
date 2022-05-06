package com.ich.reciptopia.presentation.main.search

import com.ich.reciptopia.domain.model.SearchHistoryEntity

sealed class SearchScreenEvent{
    data class AddSearchHistory(val historyEntity: SearchHistoryEntity): SearchScreenEvent()
    data class DeleteSearchHistory(val historyEntity: SearchHistoryEntity): SearchScreenEvent()
}