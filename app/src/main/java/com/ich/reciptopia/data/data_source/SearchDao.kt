package com.ich.reciptopia.data.data_source

import androidx.room.*
import com.ich.reciptopia.domain.model.FavoriteEntity
import com.ich.reciptopia.domain.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    @Query("SELECT * FROM searchhistoryentity ORDER BY id DESC")
    fun getSearchHistories(): Flow<List<SearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(historyEntity: SearchHistoryEntity)

    @Delete
    suspend fun deleteSearchHistory(historyEntity: SearchHistoryEntity)


    @Query("SELECT * FROM favoriteentity ORDER BY id DESC")
    fun getFavorites(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity)
}