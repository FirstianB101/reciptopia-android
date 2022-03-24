package com.ich.reciptopia.domain.use_case.search_history

import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow

class GetSearchHistoriesInDB(
    private val repository: SearchHistoryRepository
){
    operator fun invoke(): Flow<List<SearchHistory>>{
        return repository.getSearchHistories()
    }
}