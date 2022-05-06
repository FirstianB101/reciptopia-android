package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.data_source.SearchHistoryDao
import com.ich.reciptopia.domain.model.SearchHistoryEntity
import com.ich.reciptopia.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow

class SearchHistoryRepositoryImpl(
    private val dao: SearchHistoryDao
): SearchHistoryRepository{
    override fun getSearchHistories(): Flow<List<SearchHistoryEntity>> {
        return dao.getSearchHistories()
    }

    override suspend fun insertSearchHistory(historyEntity: SearchHistoryEntity) {
        dao.insertSearchHistory(historyEntity)
    }

    override suspend fun deleteSearchHistory(historyEntity: SearchHistoryEntity) {
        dao.deleteSearchHistory(historyEntity)
    }
}