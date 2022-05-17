package com.ich.reciptopia.domain.use_case.search

import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesFromDBUseCase(
    private val repository: SearchRepository
) {
    operator fun invoke(): Flow<List<Favorite>> {
        return repository.getFavoritesFromDB()
    }
}