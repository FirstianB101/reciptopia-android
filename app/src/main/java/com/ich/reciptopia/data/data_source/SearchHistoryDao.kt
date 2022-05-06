package com.ich.reciptopia.data.data_source

import androidx.room.*
import com.ich.reciptopia.domain.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Query("SELECT * FROM searchhistoryentity ORDER BY id DESC")
    fun getSearchHistories(): Flow<List<SearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(historyEntity: SearchHistoryEntity)

    @Delete
    suspend fun deleteSearchHistory(historyEntity: SearchHistoryEntity)
}