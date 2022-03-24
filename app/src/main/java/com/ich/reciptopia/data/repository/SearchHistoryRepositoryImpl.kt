package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.data_source.SearchHistoryDao
import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow

class SearchHistoryRepositoryImpl(
    private val dao: SearchHistoryDao
): SearchHistoryRepository{
    override fun getSearchHistories(): Flow<List<SearchHistory>> {
        return dao.getSearchHistories()
    }

    override suspend fun insertSearchHistory(history: SearchHistory) {
        dao.insertSearchHistory(history)
    }

    override suspend fun deleteSearchHistory(history: SearchHistory) {
        dao.deleteSearchHistory(history)
    }
}