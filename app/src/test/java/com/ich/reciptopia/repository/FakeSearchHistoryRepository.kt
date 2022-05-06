package com.ich.reciptopia.repository

import com.ich.reciptopia.domain.model.SearchHistoryEntity
import com.ich.reciptopia.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSearchHistoryRepository: SearchHistoryRepository {

    private val histories = mutableListOf<SearchHistoryEntity>()

    override fun getSearchHistories(): Flow<List<SearchHistoryEntity>> {
        return flow { emit(histories) }
    }

    override suspend fun insertSearchHistory(historyEntity: SearchHistoryEntity) {
        histories.add(historyEntity)
    }

    override suspend fun deleteSearchHistory(historyEntity: SearchHistoryEntity) {
        histories.remove(historyEntity)
    }
}