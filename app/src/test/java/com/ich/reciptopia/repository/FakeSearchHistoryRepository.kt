package com.ich.reciptopia.repository

import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSearchHistoryRepository: SearchHistoryRepository {

    private val histories = mutableListOf<SearchHistory>()

    override fun getSearchHistories(): Flow<List<SearchHistory>> {
        return flow { emit(histories) }
    }

    override suspend fun insertSearchHistory(history: SearchHistory) {
        histories.add(history)
    }

    override suspend fun deleteSearchHistory(history: SearchHistory) {
        histories.remove(history)
    }
}