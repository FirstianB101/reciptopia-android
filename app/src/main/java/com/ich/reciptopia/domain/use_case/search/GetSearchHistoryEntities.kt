package com.ich.reciptopia.domain.use_case.search

import com.ich.reciptopia.domain.model.SearchHistoryEntity
import com.ich.reciptopia.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetSearchHistoryEntities(
    private val repository: SearchRepository
){
    operator fun invoke(): Flow<List<SearchHistoryEntity>>{
        return repository.getSearchHistoryEntities()
    }
}