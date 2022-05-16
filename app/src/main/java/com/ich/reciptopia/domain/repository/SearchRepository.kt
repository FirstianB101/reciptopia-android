package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.FavoriteEntity
import com.ich.reciptopia.domain.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getSearchHistoryEntities(): Flow<List<SearchHistoryEntity>>

    suspend fun insertSearchHistoryEntity(historyEntity: SearchHistoryEntity)

    suspend fun deleteSearchHistoryEntity(historyEntity: SearchHistoryEntity)

    fun getFavoriteEntities(): Flow<List<FavoriteEntity>>

    suspend fun deleteFavoriteEntity(favoriteEntity: FavoriteEntity)
}