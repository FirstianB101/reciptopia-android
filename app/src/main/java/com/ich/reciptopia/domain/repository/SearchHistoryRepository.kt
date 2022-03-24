package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.SearchHistory
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {

    fun getSearchHistories(): Flow<List<SearchHistory>>

    suspend fun insertSearchHistory(history: SearchHistory)

    suspend fun deleteSearchHistory(history: SearchHistory)
}