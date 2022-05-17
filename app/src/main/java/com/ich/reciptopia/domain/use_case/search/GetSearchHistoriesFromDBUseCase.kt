package com.ich.reciptopia.domain.use_case.search

import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetSearchHistoriesFromDBUseCase(
    private val repository: SearchRepository
){
    operator fun invoke(): Flow<List<SearchHistory>>{
        return repository.getSearchHistoriesFromDB()
    }
}