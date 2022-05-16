package com.ich.reciptopia.domain.use_case.search

import com.ich.reciptopia.domain.model.FavoriteEntity
import com.ich.reciptopia.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteEntities(
    private val repository: SearchRepository
) {
    operator fun invoke(): Flow<List<FavoriteEntity>> {
        return repository.getFavoriteEntities()
    }
}