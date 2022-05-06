package com.ich.reciptopia.domain.use_case.search_history

import com.ich.reciptopia.domain.model.SearchHistoryEntity
import com.ich.reciptopia.domain.repository.SearchHistoryRepository

class AddSearchHistory(
    private val repository: SearchHistoryRepository
) {

    suspend operator fun invoke(historyEntity: SearchHistoryEntity) {
        repository.insertSearchHistory(historyEntity)
    }
}