package com.ich.reciptopia.repository

import com.ich.reciptopia.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSearchHistoryRepository: SearchRepository {

    private val histories = mutableListOf<SearchHistoryEntity>()
    private val favorites = mutableListOf<FavoriteEntity>()

    override fun getSearchHistoryEntities(): Flow<List<SearchHistoryEntity>> {
        return flow { emit(histories) }
    }

    override suspend fun insertSearchHistoryEntity(historyEntity: SearchHistoryEntity) {
        histories.add(historyEntity)
    }

    override suspend fun deleteSearchHistoryEntity(historyEntity: SearchHistoryEntity) {
        histories.remove(historyEntity)
    }

    override fun getFavoriteEntities(): Flow<List<FavoriteEntity>> {
        return flow { emit(favorites) }
    }

    override suspend fun deleteFavoriteEntity(favoriteEntity: FavoriteEntity) {
        favorites.remove(favoriteEntity)
    }
}