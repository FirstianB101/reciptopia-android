package com.ich.reciptopia.domain.use_case.search

import com.ich.reciptopia.domain.model.SearchHistoryEntity
import com.ich.reciptopia.domain.repository.SearchRepository

class AddSearchHistoryEntity(
    private val repository: SearchRepository
) {

    suspend operator fun invoke(historyEntity: SearchHistoryEntity) {
        repository.insertSearchHistoryEntity(historyEntity)
    }
}