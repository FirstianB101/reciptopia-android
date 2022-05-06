package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {

    fun getSearchHistories(): Flow<List<SearchHistoryEntity>>

    suspend fun insertSearchHistory(historyEntity: SearchHistoryEntity)

    suspend fun deleteSearchHistory(historyEntity: SearchHistoryEntity)
}