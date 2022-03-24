package com.ich.reciptopia.domain.use_case.search_history

import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.domain.repository.SearchHistoryRepository

class DeleteSearchHistory(
    private val repository: SearchHistoryRepository
) {

    suspend operator fun invoke(history: SearchHistory) {
        repository.deleteSearchHistory(history)
    }
}