package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.data_source.SearchDao
import com.ich.reciptopia.domain.model.FavoriteEntity
import com.ich.reciptopia.domain.model.SearchHistoryEntity
import com.ich.reciptopia.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl(
    private val dao: SearchDao
): SearchRepository {
    override fun getSearchHistoryEntities(): Flow<List<SearchHistoryEntity>> {
        return dao.getSearchHistories()
    }

    override suspend fun insertSearchHistoryEntity(historyEntity: SearchHistoryEntity) {
        dao.insertSearchHistory(historyEntity)
    }

    override suspend fun deleteSearchHistoryEntity(historyEntity: SearchHistoryEntity) {
        dao.deleteSearchHistory(historyEntity)
    }

    override fun getFavoriteEntities(): Flow<List<FavoriteEntity>> {
        return dao.getFavorites()
    }

    override suspend fun deleteFavoriteEntity(favoriteEntity: FavoriteEntity) {
        dao.deleteFavorite(favoriteEntity)
    }
}