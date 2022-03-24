package com.ich.reciptopia.domain.use_case.search_history

data class SearchHistoryUseCases(
    val getSearchHistories: GetSearchHistoriesInDB,
    val addSearchHistory: AddSearchHistory,
    val deleteSearchHistory: DeleteSearchHistory
)
